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
            // 1. Open de XML file en parse het document
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // 2. Normaliseer het XML-document
            document.getDocumentElement().normalize();

            // 3. Haal alle "Selection" nodes op
            NodeList selectionNodes = document.getElementsByTagName("Selection");

            // 4. Loop door elke "Selection" node
            for (int i = 0; i < selectionNodes.getLength(); i++) {
                Element selectionElement = (Element) selectionNodes.item(i);

                // 5. Haal de "CandidateIdentifier" en "ValidVotes" op
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

                // 6. Maak het CandidateVotes object aan en sla de data op
                CandidateVotes candidateVotes = new CandidateVotes();
                candidateVotes.setCandidateIdentifier(candidateIdentifier);
                candidateVotes.setValidVotes(validVotes);

                // 7. Sla het object op via de service
                candidateVotesService.saveCandidateVotes(candidateVotes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

