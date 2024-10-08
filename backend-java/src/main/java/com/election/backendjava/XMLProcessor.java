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
public class XMLProcessor {

    @Autowired
    private AffiliationRepository affiliationRepository;

    @Transactional
    public void processXML(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            NodeList affiliations = document.getElementsByTagName("AffiliationIdentifier");

            for (int i = 0; i < affiliations.getLength(); i++) {
                Element affiliationElement = (Element) affiliations.item(i);
                String affiliationId = affiliationElement.getAttribute("Id");
                String registeredName = affiliationElement.getElementsByTagName("RegisteredName").item(0).getTextContent();

                Affiliation affiliation = new Affiliation(affiliationId, registeredName);

                affiliationRepository.save(affiliation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
