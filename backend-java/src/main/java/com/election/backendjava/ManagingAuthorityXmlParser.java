package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Optional;

@Component
public class ManagingAuthorityXmlParser {

    @Autowired
    private ManagingAuthorityRepository managingAuthorityRepository;

    public void processXML(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            // Haal de 'ManagingAuthority' node op
            NodeList managingAuthorityNodes = document.getElementsByTagName("ManagingAuthority");

            if (managingAuthorityNodes.getLength() > 0) {
                Element managingAuthorityElement = (Element) managingAuthorityNodes.item(0);

                // Extract de AuthorityIdentifier en AuthorityName
                Element authorityIdentifierElement = (Element) managingAuthorityElement
                        .getElementsByTagName("AuthorityIdentifier")
                        .item(0);

                String authorityIdentifier = authorityIdentifierElement.getAttribute("Id");
                String authorityName = authorityIdentifierElement.getTextContent();

                // Controleer of de ManagingAuthority al bestaat
                Optional<ManagingAuthority> existingAuthority = managingAuthorityRepository.findByAuthorityIdentifier(authorityIdentifier);
                if (existingAuthority.isPresent()) {
                    System.out.println("Managing Authority met identifier " + authorityIdentifier + " bestaat al, overslaan.");
                } else {
                    // Nieuwe ManagingAuthority aanmaken en opslaan in de database
                    ManagingAuthority managingAuthority = new ManagingAuthority(authorityIdentifier, authorityName);
                    managingAuthorityRepository.save(managingAuthority);
                    System.out.println("Managing Authority met identifier " + authorityIdentifier + " succesvol opgeslagen.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
