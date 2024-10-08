package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class PartyVotesParser {

    @Autowired
    private PartyVotesRepository partyVotesRepository; // Assuming you have a repository for saving PartyVotes

    public List<PartyVotes> parseXML(String filePath) {
        List<PartyVotes> partyVotesList = new ArrayList<>();

        try {
            // Create DocumentBuilderFactory and DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();

            // Get all ReportingUnitVotes elements
            NodeList reportingUnits = document.getElementsByTagName("ReportingUnitVotes");

            for (int i = 0; i < reportingUnits.getLength(); i++) {
                Element reportingUnit = (Element) reportingUnits.item(i);

                // Haal de ReportingUnitIdentifier op
                String reportingUnitId = reportingUnit.getElementsByTagName("ReportingUnitIdentifier").item(0).getAttributes().getNamedItem("Id").getNodeValue();
                String reportingUnitIdentifier = reportingUnit.getElementsByTagName("ReportingUnitIdentifier").item(0).getTextContent();

                // Splits de reportingUnitId om alleen het eerste deel te behouden (voor de ID)
                if (reportingUnitId.contains("::")) {
                    reportingUnitId = reportingUnitId.split("::")[0]; // Neem alleen het deel vóór "::"
                }

                // Extract postal code and clean ReportingUnitIdentifier
                String postalCode = extractPostalCode(reportingUnitIdentifier);
                String cleanReportingUnitIdentifier = removePostalCode(reportingUnitIdentifier, postalCode);

                // Get Affiliation and ValidVotes
                NodeList selections = reportingUnit.getElementsByTagName("Selection");
                for (int j = 0; j < selections.getLength(); j++) {
                    Element selection = (Element) selections.item(j);

                    if (selection.getElementsByTagName("AffiliationIdentifier").getLength() > 0) {
                        int affiliationId = Integer.parseInt(selection.getElementsByTagName("AffiliationIdentifier").item(0).getAttributes().getNamedItem("Id").getNodeValue());
                        int validVotes = Integer.parseInt(selection.getElementsByTagName("ValidVotes").item(0).getTextContent());

                        PartyVotes partyVotes = new PartyVotes();
                        partyVotes.setValidVotes(validVotes);
                        partyVotes.setReportingUnitIdentifier(cleanReportingUnitIdentifier); // Opslaan zonder postcode
                        partyVotes.setReportingUnitId(reportingUnitId); // De aangepaste ID zonder "::SB1"
                        partyVotes.setAffiliationId(affiliationId);

                        partyVotesList.add(partyVotes);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Save the party votes to the database
        partyVotesRepository.saveAll(partyVotesList);

        return partyVotesList;
    }

    // Methode om de volledige postcode te extraheren uit de ReportingUnitIdentifier
    private String extractPostalCode(String reportingUnitIdentifier) {
        // Zoek de postcode in de ReportingUnitIdentifier
        String[] parts = reportingUnitIdentifier.split("\\s+"); // Splits op spaties
        String postalCode = ""; // Lege postcode initiëren

        // Loop door de delen om de postcode te vinden
        for (String part : parts) {
            if (part.matches("\\d{4}\\s?[A-Z]{2}")) { // Controleer of het een geldige postcode is
                postalCode = part.replaceAll("\\s+", ""); // Verwijder spaties (indien aanwezig)
                break; // Stop met zoeken na het vinden van de postcode
            }
        }

        return postalCode;
    }

    // Methode om de postcode uit de ReportingUnitIdentifier te verwijderen
    private String removePostalCode(String reportingUnitIdentifier, String postalCode) {
        return reportingUnitIdentifier.replace(postalCode, "").replaceAll("\\s+", "").trim(); // Verwijder de postcode en trim eventuele extra spaties
    }
}
