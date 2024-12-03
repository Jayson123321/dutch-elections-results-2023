package com.election.backendjava.processors;

import com.election.backendjava.entities.CandidateAffiliationVotes;
import com.election.backendjava.repositories.CandidateAffiliationVotesRepository;
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
public class CandidateAffiliationVoteParser {

    @Autowired
    private CandidateAffiliationVotesRepository candidateAffiliationVotesRepository;

    @PostConstruct
    public void parseAndSaveVotes() {
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

        NodeList contestNodes = doc.getElementsByTagName("Contest");

        for (int i = 0; i < contestNodes.getLength(); i++) {
            Element contestElement = (Element) contestNodes.item(i);

            // Haal Contest Identifier
            NodeList contestIdentifierNodes = contestElement.getElementsByTagName("ContestIdentifier");
            if (contestIdentifierNodes.getLength() == 0) {
                System.err.println("No ContestIdentifier found in Contest " + i);
                continue;
            }

            Element contestIdentifierElement = (Element) contestIdentifierNodes.item(0);
            String authorityIdentifierStr = contestIdentifierElement.getAttribute("Id");
            if (authorityIdentifierStr.isEmpty()) {
                System.err.println("Authority Identifier is empty in Contest " + i);
                continue;
            }



            Element totalVotesElement = (Element) contestElement.getElementsByTagName("TotalVotes").item(0);


            Long authorityIdentifier = Long.parseLong(authorityIdentifierStr);
            System.out.println("Authority Identifier: " + authorityIdentifier);

            Long affiliationId = null;
            String registeredName = null;
            // Verwerk de Selections
            NodeList selectionNodes = totalVotesElement.getElementsByTagName("Selection");
            for (int j = 0; j < selectionNodes.getLength(); j++) {
                Element selectionElement = (Element) selectionNodes.item(j);

                // Verkrijg Candidate Identifier (verplicht)
                Long candidateIdentifierId = null;
                NodeList candidateNodes = selectionElement.getElementsByTagName("CandidateIdentifier");
                if (candidateNodes.getLength() > 0) {
                    String candidateIdentifierStr = candidateNodes.item(0).getAttributes().getNamedItem("Id").getNodeValue();
                    if (!candidateIdentifierStr.isEmpty()) {
                        candidateIdentifierId = Long.parseLong(candidateIdentifierStr);
                    } else {
                        System.err.println("Candidate Identifier is empty in Selection " + j);
                        continue;
                    }
                } else {
                    System.err.println("No CandidateIdentifier found in Selection " + j);
//                    continue;
                }

                // Verkrijg Affiliation Identifier (indien aanwezig)

                NodeList affiliationNodes = selectionElement.getElementsByTagName("AffiliationIdentifier");
                if (affiliationNodes.getLength() > 0) {
                    Element affiliationElement = (Element) affiliationNodes.item(0);
                    String affiliationIdStr = affiliationElement.getAttributes().getNamedItem("Id").getNodeValue();
                    if (!affiliationIdStr.isEmpty()) {
                        affiliationId = Long.parseLong(affiliationIdStr);
                    } else {
                        System.err.println("Affiliation Identifier is empty in Selection " + j);
                        continue;
                    }
                    registeredName = affiliationElement.getAttribute("RegisteredName");
                } else {
                    // Log een waarschuwing als er geen AffiliationIdentifier is in de selectie
                    System.err.println("No AffiliationIdentifier found in Selection " + j);

                    // We gaan nu zoeken naar de AffiliationIdentifier buiten de Selection
                    NodeList affiliationSelectionNodes = contestElement.getElementsByTagName("Selection");
                    for (int k = 0; k < affiliationSelectionNodes.getLength(); k++) {
                        Element affiliationSelection = (Element) affiliationSelectionNodes.item(k);
                        NodeList affiliationIdentifierNodes = affiliationSelection.getElementsByTagName("AffiliationIdentifier");
                        if (affiliationIdentifierNodes.getLength() > 0) {
                            Element affiliationElement = (Element) affiliationIdentifierNodes.item(0);
                            affiliationId = Long.parseLong(affiliationElement.getAttributes().getNamedItem("Id").getNodeValue());
                            registeredName = affiliationElement.getAttribute("RegisteredName");
                            break;  // Break als we het vinden
                        }
                    }
                    if (affiliationId == null) {
                        System.err.println("No AffiliationIdentifier found in entire contest");
                        continue;
                    }
                }

                // Haal Valid Votes op
                NodeList validVotesNodes = selectionElement.getElementsByTagName("ValidVotes");
                if (validVotesNodes.getLength() == 0) {
                    System.err.println("Valid Votes not found in Selection " + j);
                    continue;
                }

                String validVotesStr = validVotesNodes.item(0).getTextContent();
                if (validVotesStr.isEmpty()) {
                    System.err.println("Valid Votes is empty in Selection " + j);
                    continue;
                }

                int validVotes = Integer.parseInt(validVotesStr);

                // Maak en sla CandidateAffiliationVotes-object op
                CandidateAffiliationVotes votesRecord = new CandidateAffiliationVotes();
                votesRecord.setCandidateIdentifierId(candidateIdentifierId);
                votesRecord.setAffiliationId(affiliationId);
                votesRecord.setAuthorityIdentifier(authorityIdentifier);
                votesRecord.setValidVotes(validVotes);
                votesRecord.setRegisteredName(registeredName);

                try {
                    candidateAffiliationVotesRepository.save(votesRecord);
                    System.out.println("Saved record: " + votesRecord);
                } catch (Exception e) {
                    System.err.println("Error saving record for Selection " + j + ": " + e.getMessage());
                }
            }
        }
    }
}