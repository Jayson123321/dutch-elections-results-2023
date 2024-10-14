package com.election.backendjava.processors;

import com.election.backendjava.ReportingUnit;
import com.election.backendjava.entities.ManagingAuthority;
import com.election.backendjava.repositories.ManagingAuthorityRepository;
import com.election.backendjava.repositories.ReportingUnitRepository;
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
                        String managingAuthorityNumber = extractManagingAuthorityNumber(reportingUnitIdentifierElement.getAttribute("Id"));

                        // Maak een nieuwe ReportingUnit aan met de naam en de bijbehorende ManagingAuthority
                        ReportingUnit reportingUnit = new ReportingUnit(reportingUnitName, managingAuthority, managingAuthorityNumber);

                        // Sla de ReportingUnit op in de database
                        reportingUnitRepository.save(reportingUnit);
                        System.out.println("Reporting Unit " + reportingUnitName + " succesvol opgeslagen.");
                    }
                } else {
                    System.out.println("Geen Managing Authority gevonden met identifier " + authorityIdentifier);
                }
            }
        } catch (Exception e) {
            System.out.println("Fout bij het verwerken van het XML-bestand: " + e.getMessage());
        }
    }

    private String extractManagingAuthorityNumber(String reportingUnitId) {
        // Splits de ID om het nummer te extraheren
        String[] parts = reportingUnitId.split("::");
        return parts.length > 1 ? parts[1] : null;  // Neem het nummer na '::'
    }
}

