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
public class ReportingUnitXmlParser {

    @Autowired
    private ReportingUnitRepository reportingUnitRepository;

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

                // Haal de AuthorityIdentifier op
                Element authorityIdentifierElement = (Element) managingAuthorityElement
                        .getElementsByTagName("AuthorityIdentifier")
                        .item(0);

                String authorityIdentifier = authorityIdentifierElement.getAttribute("Id");

                // Zoek de bijbehorende ManagingAuthority in de database
                Optional<ManagingAuthority> managingAuthorityOpt = managingAuthorityRepository.findByAuthorityIdentifier(authorityIdentifier);

                if (managingAuthorityOpt.isPresent()) {
                    ManagingAuthority managingAuthority = managingAuthorityOpt.get();

                    // Haal de ReportingUnitVotes nodes op
                    NodeList reportingUnitVotesNodes = document.getElementsByTagName("ReportingUnitVotes");

                    for (int i = 0; i < reportingUnitVotesNodes.getLength(); i++) {
                        Element reportingUnitVotesElement = (Element) reportingUnitVotesNodes.item(i);

                        // Haal de ReportingUnitIdentifier op
                        Element reportingUnitIdentifierElement = (Element) reportingUnitVotesElement
                                .getElementsByTagName("ReportingUnitIdentifier")
                                .item(0);

                        String reportingUnitName = reportingUnitIdentifierElement.getTextContent();
                        String nameWithoutPostalCode = extractNameWithoutPostalCode(reportingUnitName);

                        // Maak een nieuwe ReportingUnit aan zonder postcode
                        ReportingUnit reportingUnit = new ReportingUnit(nameWithoutPostalCode, null, managingAuthority);

                        // Sla de ReportingUnit op in de database
                        reportingUnitRepository.save(reportingUnit);
                        System.out.println("Reporting Unit " + nameWithoutPostalCode + " succesvol opgeslagen.");
                    }
                } else {
                    System.out.println("Geen Managing Authority gevonden met identifier " + authorityIdentifier);
                }
            }
        } catch (Exception e) {
            System.out.println("Fout bij het verwerken van het XML-bestand: " + e.getMessage());
        }
    }

    private String extractNameWithoutPostalCode(String reportingUnitName) {
        // Neem alleen de naam tot de substring "(postcode:" als die er is.
        int index = reportingUnitName.indexOf("(postcode:");
        if (index > 0) {
            return reportingUnitName.substring(0, index).trim();
        }
        return reportingUnitName.trim(); // Geen postcode aanwezig, retourneer de volledige naam
    }
}
