package com.election.backendjava.processors;

import com.election.backendjava.entities.CandidateVotes;
import com.election.backendjava.services.CandidateVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Component
public class CandidateVoteParser {

    @Autowired
    private CandidateVotesService candidateVotesService;

    public void parseAndSaveVotes(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            NodeList selectionNodes = document.getElementsByTagName("Selection");

            for (int i = 0; i < selectionNodes.getLength(); i++) {
                Element selectionElement = (Element) selectionNodes.item(i);

                String candidateIdentifier = selectionElement
                        .getElementsByTagName("CandidateIdentifier")
                        .item(0)
                        .getAttributes()
                        .getNamedItem("ShortCode")
                        .getNodeValue();

                int validVotes = Integer.parseInt(selectionElement
                        .getElementsByTagName("ValidVotes")
                        .item(0)
                        .getTextContent());

                CandidateVotes candidateVotes = new CandidateVotes();
                candidateVotes.setCandidateIdentifier(candidateIdentifier);
                candidateVotes.setCandidateVoteCount(validVotes);
                // Set other fields as needed
                candidateVotes.setAffiliationId("affiliation_id"); // Example
                candidateVotes.setManagingAuthorityIdentifier("managing_authority_identifier"); // Example
                candidateVotes.setReportingUnitId("reporting_unit_id"); // Example

                candidateVotesService.saveCandidateVotes(candidateVotes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}