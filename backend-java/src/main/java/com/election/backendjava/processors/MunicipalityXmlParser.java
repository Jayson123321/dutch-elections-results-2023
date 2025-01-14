package com.election.backendjava.processors;

import com.election.backendjava.entities.Municipality;
import com.election.backendjava.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Component
public class MunicipalityXmlParser {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    public void parseXmlFile(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList municipalityNodes = doc.getElementsByTagName("Municipality");

            for (int i = 0; i < municipalityNodes.getLength(); i++) {
                Element municipalityElement = (Element) municipalityNodes.item(i);

                String municipalityId = municipalityElement.getAttribute("Id");
                String municipalityName = municipalityElement.getElementsByTagName("Name").item(0).getTextContent();

                Municipality municipality = new Municipality(municipalityId, municipalityName);
                municipalityRepository.save(municipality);

                System.out.println("Municipality " + municipalityId + " saved.");
            }
        } catch (Exception e) {
            System.err.println("Error parsing XML file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
