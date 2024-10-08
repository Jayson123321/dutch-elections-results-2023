package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Component
public class ContestXmlProcessor {

    @Autowired
    private ContestRepository contestRepository;

    @Transactional
    public void processXML(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            // Ophalen van alle contests
            NodeList contestNodes = document.getElementsByTagName("Contest");

            for (int i = 0; i < contestNodes.getLength(); i++) {
                Element contestElement = (Element) contestNodes.item(i);

                // Extracting contest identifier
                String contestIdentifier = contestElement
                        .getElementsByTagName("ContestIdentifier")
                        .item(0)
                        .getAttributes()
                        .getNamedItem("Id")
                        .getNodeValue();

                // Extracting contest name
                String contestName = contestElement
                        .getElementsByTagName("ContestName")
                        .item(0)
                        .getTextContent();

                // Maak een nieuw Contest object
                Contest contest = new Contest(contestIdentifier, contestName);

                // Sla de contest op in de database
                contestRepository.save(contest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
