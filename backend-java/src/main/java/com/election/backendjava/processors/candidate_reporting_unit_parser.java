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
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        String municipalityName = getMunicipalityName(doc);
        if (municipalityName == null) {
            System.err.println("MunicipalityName not found in the XML document.");
            return;
        }

        NodeList reportingUnits = doc.getElementsByTagName("ReportingUnitVotes");

        for (int i = 0; i < reportingUnits.getLength(); i++) {
            Element reportingUnit = (Element) reportingUnits.item(i);
            Element reportingUnitIdentifier = (Element) reportingUnit.getElementsByTagName("ReportingUnitIdentifier").item(0);

            String reportingUnitId = reportingUnitIdentifier.getAttribute("Id");
            String reportingUnitName = reportingUnitIdentifier.getTextContent();

            NodeList selections = reportingUnit.getElementsByTagName("Selection");
            for (int j = 0; j < selections.getLength(); j++) {
                Element selection = (Element) selections.item(j);

                Long candidateId = getCandidateId(selection);
                String affiliationName = getAffiliationName(selection);
                int validVotes = getValidVotes(selection);

                if (candidateId == null) {
                    System.err.println("Candidate ID not found for selection in Reporting Unit: " + reportingUnitId);
                    continue;
                }

                candidate_reporting_unit_votes result = new candidate_reporting_unit_votes();
                result.setMunicipalityName(municipalityName);
                result.setReportingUnitId(reportingUnitId);
                result.setReportingUnitName(reportingUnitName);
                result.setCandidateId(candidateId);
                result.setAffiliationName(affiliationName);
                result.setValidVotes(validVotes);

                try {
                    candidate_reporting_unit_votes_repository.save(result);
                    System.out.println("Saved result: " + result);
                } catch (Exception e) {
                    System.err.println("Error saving result for Candidate ID " + candidateId + ": " + e.getMessage());
                }
            }
        }
    }

    private String getMunicipalityName(Document doc) {
        // Get the municipality name from <ManagingAuthority> -> <AuthorityIdentifier>
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
                return registeredNameNodes.item(0).getTextContent();
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
