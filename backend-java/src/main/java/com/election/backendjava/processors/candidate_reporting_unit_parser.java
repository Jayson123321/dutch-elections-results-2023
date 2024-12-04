package com.election.backendjava.processors;

import com.election.backendjava.entities.candidate_reporting_unit_votes;
import com.election.backendjava.repositories.candidate_reporting_unit_votes_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jakarta.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Component
public class candidate_reporting_unit_parser {

    @Autowired
    private candidate_reporting_unit_votes_repository candidate_reporting_unit_votes_repository;

    @PostConstruct
    public void parseAndSaveResults() {
        String directoryPath = "C:\\Users\\ruben\\IdeaProjects\\wiipuujaamee42\\xmlfiles\\";
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Directory not found or is not a directory: " + directoryPath);
            return;
        }

        File[] xmlFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        if (xmlFiles == null || xmlFiles.length == 0) {
            System.err.println("No XML files found in directory: " + directoryPath);
            return;
        }

        for (File xmlFile : xmlFiles) {
            System.out.println("Processing file: " + xmlFile.getName());
            try {
                parseFile(xmlFile);
            } catch (Exception e) {
                System.err.println("Error processing file " + xmlFile.getName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void parseFile(File xmlFile) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true); // Zorg dat namespaces worden ondersteund
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        String municipalityName = getMunicipalityName(doc);
        if (municipalityName == null) {
            System.err.println("MunicipalityName not found in the XML document.");
            return;
        }

        // Zoek naar alle ReportingUnitVotes buiten TotalVotes
        NodeList reportingUnits = doc.getElementsByTagName("ReportingUnitVotes");

        String lastAffiliationId = null;
        String lastAffiliationName = null;

        for (int i = 0; i < reportingUnits.getLength(); i++) {
            Element reportingUnit = (Element) reportingUnits.item(i);
            Element reportingUnitIdentifier = (Element) reportingUnit.getElementsByTagName("ReportingUnitIdentifier").item(0);

            String reportingUnitId = reportingUnitIdentifier.getAttribute("Id");
            String reportingUnitName = reportingUnitIdentifier.getTextContent();

            // Zoek naar de selections die onder de ReportingUnitVotes vallen
            NodeList selections = reportingUnit.getElementsByTagName("Selection");
            for (int j = 0; j < selections.getLength(); j++) {
                Element selection = (Element) selections.item(j);

                // Controleer of er een AffiliationIdentifier aanwezig is
                String affiliationId = getAffiliationId(selection);
                String affiliationName = getAffiliationName(selection);

                // Als er een AffiliationIdentifier is, sla deze dan op
                if (affiliationId != null && affiliationName != null) {
                    lastAffiliationId = affiliationId;
                    lastAffiliationName = affiliationName;
                    System.out.println("New Affiliation ID: " + affiliationId + ", Name: " + affiliationName);
                } else {
                    // Anders gebruiken we de laatste bekende affiliationId en affiliationName
                    affiliationId = lastAffiliationId;
                    affiliationName = lastAffiliationName;
                }

                // Haal het CandidateId en validVotes
                Long candidateId = getCandidateId(selection);
                int validVotes = getValidVotes(selection);

                // Sla de resultaten op voor zowel AffiliationIdentifier als Candidate
                if (affiliationId != null || affiliationName != null) {
                    candidate_reporting_unit_votes result = new candidate_reporting_unit_votes();
                    result.setMunicipalityName(municipalityName);
                    result.setReportingUnitId(reportingUnitId);
                    result.setReportingUnitName(reportingUnitName);
                    result.setAffiliationName(affiliationName);
                    result.setAffiliationId(affiliationId);
                    result.setValidVotes(validVotes);

                    try {
                        candidate_reporting_unit_votes_repository.save(result);
                        System.out.println("Saved Affiliation result: " + result);
                    } catch (Exception e) {
                        System.err.println("Error saving result for Affiliation: " + e.getMessage());
                    }
                }

                if (candidateId != null) {
                    candidate_reporting_unit_votes result = new candidate_reporting_unit_votes();
                    result.setMunicipalityName(municipalityName);
                    result.setReportingUnitId(reportingUnitId);
                    result.setReportingUnitName(reportingUnitName);
                    result.setCandidateId(candidateId);
                    result.setValidVotes(validVotes);

                    try {
                        candidate_reporting_unit_votes_repository.save(result);
                        System.out.println("Saved Candidate result: " + result);
                    } catch (Exception e) {
                        System.err.println("Error saving result for Candidate ID " + candidateId + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    private String getMunicipalityName(Document doc) {
        NodeList authorityIdentifierNodes = doc.getElementsByTagName("AuthorityIdentifier");
        if (authorityIdentifierNodes.getLength() > 0) {
            return authorityIdentifierNodes.item(0).getTextContent();
        }
        return null;
    }

    private Long getCandidateId(Element selection) {
        NodeList candidateNodes = selection.getElementsByTagName("CandidateIdentifier");
        if (candidateNodes.getLength() > 0) {
            String candidateIdStr = candidateNodes.item(0).getAttributes().getNamedItem("Id").getNodeValue();
            return Long.parseLong(candidateIdStr);
        }
        return null;
    }

    private String getAffiliationName(Element selection) {
        NodeList affiliationNodes = selection.getElementsByTagName("AffiliationIdentifier");
        if (affiliationNodes.getLength() > 0) {
            Element affiliation = (Element) affiliationNodes.item(0);
            NodeList registeredNameNodes = affiliation.getElementsByTagName("RegisteredName");
            if (registeredNameNodes.getLength() > 0) {
                return registeredNameNodes.item(0).getTextContent().trim();
            }
        }
        return null;
    }

    private String getAffiliationId(Element selection) {
        NodeList affiliationNodes = selection.getElementsByTagName("AffiliationIdentifier");
        if (affiliationNodes.getLength() > 0) {
            Element affiliation = (Element) affiliationNodes.item(0);
            if (affiliation.hasAttribute("Id")) {
                return affiliation.getAttribute("Id");
            }
        }
        return null;
    }

    private int getValidVotes(Element selection) {
        NodeList validVotesNodes = selection.getElementsByTagName("ValidVotes");
        if (validVotesNodes.getLength() > 0) {
            return Integer.parseInt(validVotesNodes.item(0).getTextContent());
        }
        return 0;
    }
}
