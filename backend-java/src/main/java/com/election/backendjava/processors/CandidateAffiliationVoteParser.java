//package com.election.backendjava.processors;
//
//import com.election.backendjava.entities.CandidateAffiliationVotes;
//import com.election.backendjava.repositories.CandidateAffiliationVotesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//
//import jakarta.annotation.PostConstruct;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.io.File;
//
//@Component
//public class CandidateAffiliationVoteParser {
//
//    @Autowired
//    private CandidateAffiliationVotesRepository candidateAffiliationVotesRepository;
//
//    @PostConstruct
//    public void parseAndSaveVotes() {
//        String directoryPath = "C:\\Users\\ruben\\IdeaProjects\\wiipuujaamee42\\xmlfiles\\";
//        File directory = new File(directoryPath);
//
//        if (!directory.exists() || !directory.isDirectory()) {
//            System.err.println("Directory not found or is not a directory: " + directoryPath);
//            return;
//        }
//
//        File[] xmlFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
//
//        if (xmlFiles == null || xmlFiles.length == 0) {
//            System.err.println("No XML files found in directory: " + directoryPath);
//            return;
//        }
//
//        for (File xmlFile : xmlFiles) {
//            System.out.println("Processing file: " + xmlFile.getName());
//            try {
//                parseFile(xmlFile);
//            } catch (Exception e) {
//                System.err.println("Error processing file " + xmlFile.getName() + ": " + e.getMessage());
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void parseFile(File xmlFile) throws Exception {
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//        Document doc = dBuilder.parse(xmlFile);
//        doc.getDocumentElement().normalize();
//
//        // Retrieve AuthorityIdentifier and AuthorityName
//        NodeList authorityIdentifierNodes = doc.getElementsByTagName("AuthorityIdentifier");
//        if (authorityIdentifierNodes.getLength() == 0) {
//            System.err.println("No AuthorityIdentifier found in the document");
//            return;
//        }
//
//        Element authorityIdentifierElement = (Element) authorityIdentifierNodes.item(0);
//        String authorityIdentifierStr = authorityIdentifierElement.getAttribute("Id");
//        if (authorityIdentifierStr.isEmpty()) {
//            System.err.println("Authority Identifier is empty");
//            return;
//        }
//
//        String authorityName = authorityIdentifierElement.getTextContent();
//        Long authorityIdentifier = Long.parseLong(authorityIdentifierStr);
//        System.out.println("Authority Identifier: " + authorityIdentifier);
//        System.out.println("Authority Name: " + authorityName);
//
//        NodeList contestNodes = doc.getElementsByTagName("Contest");
//
//        for (int i = 0; i < contestNodes.getLength(); i++) {
//            Element contestElement = (Element) contestNodes.item(i);
//
//            // Process the Contest Selections
//            NodeList selectionNodes = contestElement.getElementsByTagName("Selection");
//            for (int j = 0; j < selectionNodes.getLength(); j++) {
//                Element selectionElement = (Element) selectionNodes.item(j);
//
//                Long candidateIdentifierId = null;
//                Long affiliationId = null;
//                String registeredName = null;
//
//                // Retrieve Candidate Identifier (mandatory)
//                NodeList candidateNodes = selectionElement.getElementsByTagName("CandidateIdentifier");
//                if (candidateNodes.getLength() > 0) {
//                    String candidateIdentifierStr = candidateNodes.item(0).getAttributes().getNamedItem("Id").getNodeValue();
//                    if (!candidateIdentifierStr.isEmpty()) {
//                        candidateIdentifierId = Long.parseLong(candidateIdentifierStr);
//                    } else {
//                        System.err.println("Candidate Identifier is empty in Selection " + j);
//                        continue;
//                    }
//                } else {
//                    System.err.println("No CandidateIdentifier found in Selection " + j);
//                    continue;
//                }
//
//                // Retrieve Affiliation Identifier and RegisteredName
//                NodeList affiliationNodes = selectionElement.getElementsByTagName("AffiliationIdentifier");
//                if (affiliationNodes.getLength() > 0) {
//                    Element affiliationElement = (Element) affiliationNodes.item(0);
//                    String affiliationIdStr = affiliationElement.getAttribute("Id");
//                    if (!affiliationIdStr.isEmpty()) {
//                        affiliationId = Long.parseLong(affiliationIdStr);
//                    } else {
//                        System.err.println("Affiliation Identifier is empty in Selection " + j);
//                        continue;
//                    }
//                    NodeList registeredNameNodes = affiliationElement.getElementsByTagName("RegisteredName");
//                    if (registeredNameNodes.getLength() > 0) {
//                        registeredName = registeredNameNodes.item(0).getTextContent();
//                    } else {
//                        System.err.println("RegisteredName not found in AffiliationIdentifier");
//                    }
//                } else {
//                    // Fallback: check for AffiliationIdentifier outside Selection
//                    NodeList contestAffiliationNodes = contestElement.getElementsByTagName("AffiliationIdentifier");
//                    if (contestAffiliationNodes.getLength() > 0) {
//                        Element affiliationElement = (Element) contestAffiliationNodes.item(0);
//                        String affiliationIdStr = affiliationElement.getAttribute("Id");
//                        if (!affiliationIdStr.isEmpty()) {
//                            affiliationId = Long.parseLong(affiliationIdStr);
//                        }
//                        NodeList registeredNameNodes = affiliationElement.getElementsByTagName("RegisteredName");
//                        if (registeredNameNodes.getLength() > 0) {
//                            registeredName = registeredNameNodes.item(0).getTextContent();
//                        } else {
//                            System.err.println("RegisteredName not found in fallback AffiliationIdentifier");
//                        }
//                    } else {
//                        System.err.println("No AffiliationIdentifier found in entire contest for Selection " + j);
//                        continue;
//                    }
//                }
//
//                // Retrieve Valid Votes
//                NodeList validVotesNodes = selectionElement.getElementsByTagName("ValidVotes");
//                if (validVotesNodes.getLength() == 0) {
//                    System.err.println("Valid Votes not found in Selection " + j);
//                    continue;
//                }
//
//                String validVotesStr = validVotesNodes.item(0).getTextContent();
//                if (validVotesStr.isEmpty()) {
//                    System.err.println("Valid Votes is empty in Selection " + j);
//                    continue;
//                }
//
//                int validVotes = Integer.parseInt(validVotesStr);
//
//                // Create and save CandidateAffiliationVotes object
//                CandidateAffiliationVotes votesRecord = new CandidateAffiliationVotes();
//                votesRecord.setCandidateIdentifierId(candidateIdentifierId);
//                votesRecord.setAffiliationId(affiliationId);
//                votesRecord.setAuthorityIdentifier(authorityIdentifier);
//                votesRecord.setAuthorityName(authorityName);
//                votesRecord.setValidVotes(validVotes);
//                votesRecord.setRegisteredName(registeredName);
//
//                try {
//                    candidateAffiliationVotesRepository.save(votesRecord);
//                    System.out.println("Saved record: " + votesRecord);
//                } catch (Exception e) {
//                    System.err.println("Error saving record for Selection " + j + ": " + e.getMessage());
//                }
//            }
//        }
//    }
//}
