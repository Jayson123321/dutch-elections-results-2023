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
import java.io.FileNotFoundException;

@Component
public class CandidateAffiliationVoteParser {

    @Autowired
    private CandidateAffiliationVotesRepository candidateAffiliationVotesRepository;

    @PostConstruct
    public void parseAndSaveVotes() {
        String directoryPath = "C:\\Users\\ruben\\IdeaProjects\\wiipuujaamee42\\xmlfiles\\";
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Directory not found or it is not a directory: " + directoryPath);
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
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + xmlFile.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Error processing file " + xmlFile.getName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void parseFile(File xmlFile) throws Exception {
        if (!xmlFile.exists()) {
            throw new FileNotFoundException("File not found: " + xmlFile.getAbsolutePath());
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList contestNodes = doc.getElementsByTagName("contest");

        for (int i = 0; i < contestNodes.getLength(); i++) {
            Element contestElement = (Element) contestNodes.item(i);

            // Extract Contest Identifier
            NodeList contestIdentifierNodes = contestElement.getElementsByTagName("contestidentifier");
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

            Long authorityIdentifier = Long.parseLong(authorityIdentifierStr);
            System.out.println("Authority Identifier: " + authorityIdentifier);

            // Process Selections
            NodeList selectionNodes = contestElement.getElementsByTagName("selection");
            for (int j = 0; j < selectionNodes.getLength(); j++) {
                Element selectionElement = (Element) selectionNodes.item(j);

                // Parse Candidate Identifier
                NodeList candidateIdentifierNodes = selectionElement.getElementsByTagName("candidateidentifier");
                Long candidateIdentifierId = null;
                if (candidateIdentifierNodes.getLength() > 0) {
                    String candidateIdentifierStr = candidateIdentifierNodes.item(0).getAttributes().getNamedItem("Id").getNodeValue();
                    if (!candidateIdentifierStr.isEmpty()) {
                        candidateIdentifierId = Long.parseLong(candidateIdentifierStr);
                    } else {
                        System.err.println("Candidate Identifier is empty in Selection " + j);
                        continue;
                    }
                } else {
                    System.err.println("No CandidateIdentifier found in Selection " + j);
                    continue;
                }

                // Parse Affiliation Identifier (mandatory)
                NodeList affiliationNodes = selectionElement.getElementsByTagName("affiliationidentifier");
                String affiliationId = null;
                if (affiliationNodes.getLength() > 0) {
                    String affiliationIdStr = affiliationNodes.item(0).getAttributes().getNamedItem("Id").getNodeValue();
                    if (!affiliationIdStr.isEmpty()) {
                        affiliationId = affiliationIdStr;
                    } else {
                        System.err.println("Affiliation Identifier is empty in Selection " + j);
                        continue;
                    }
                } else {
                    System.err.println("Affiliation Identifier missing in Selection " + j);
                    continue;
                }

                // Parse Valid Votes
                NodeList validVotesNodes = selectionElement.getElementsByTagName("validvotes");
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

                // Create and Save CandidateAffiliationVotes object
                CandidateAffiliationVotes votesRecord = new CandidateAffiliationVotes();
                votesRecord.setCandidateIdentifierId(candidateIdentifierId);
                votesRecord.setAffiliationId(affiliationId); // Set affiliationId from AffiliationIdentifier
                votesRecord.setAuthorityIdentifier(authorityIdentifier);
                votesRecord.setValidVotes(validVotes);

                System.out.println("Saving record: " + votesRecord);
                try {
                    candidateAffiliationVotesRepository.save(votesRecord);
                    System.out.println("Saved record: " + votesRecord);
                } catch (Exception e) {
                    System.err.println("Error saving record: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}