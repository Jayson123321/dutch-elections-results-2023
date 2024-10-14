package com.election.backendjava.processors;

import com.election.backendjava.PartyVotes;
import com.election.backendjava.entities.Affiliation;
import com.election.backendjava.repositories.PartyVotesRepository;
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
    private PartyVotesRepository partyVotesRepository;

    public List<PartyVotes> parseXML(String filePath) {
        List<PartyVotes> partyVotesList = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();

            NodeList reportingUnits = document.getElementsByTagName("ReportingUnitVotes");

            for (int i = 0; i < reportingUnits.getLength(); i++) {
                Element reportingUnit = (Element) reportingUnits.item(i);

                String reportingUnitId = reportingUnit.getElementsByTagName("ReportingUnitIdentifier").item(0).getAttributes().getNamedItem("Id").getNodeValue();
                String reportingUnitIdentifier = reportingUnit.getElementsByTagName("ReportingUnitIdentifier").item(0).getTextContent();

                if (reportingUnitId.contains("::")) {
                    reportingUnitId = reportingUnitId.split("::")[0];
                }

                String postalCode = extractPostalCode(reportingUnitIdentifier);
                String cleanReportingUnitIdentifier = removePostalCode(reportingUnitIdentifier, postalCode);

                // Extract managing authority number (assuming it's the part after "::")
                String managingAuthorityNumber = reportingUnit.getElementsByTagName("ReportingUnitIdentifier").item(0).getAttributes().getNamedItem("Id").getNodeValue();
                if (managingAuthorityNumber.contains("::")) {
                    managingAuthorityNumber = managingAuthorityNumber.split("::")[1];
                }

                NodeList selections = reportingUnit.getElementsByTagName("Selection");
                for (int j = 0; j < selections.getLength(); j++) {
                    Element selection = (Element) selections.item(j);

                    if (selection.getElementsByTagName("AffiliationIdentifier").getLength() > 0) {
                        String affiliationId = selection.getElementsByTagName("AffiliationIdentifier").item(0).getAttributes().getNamedItem("Id").getNodeValue();
                        int validVotes = Integer.parseInt(selection.getElementsByTagName("ValidVotes").item(0).getTextContent());

                        PartyVotes partyVotes = new PartyVotes();
                        partyVotes.setValidVotes(validVotes);
                        partyVotes.setReportingUnitIdentifier(cleanReportingUnitIdentifier);
                        partyVotes.setReportingUnitId(reportingUnitId);
                        partyVotes.setAffiliation(new Affiliation());
                        partyVotes.setManagingAuthorityNumber(managingAuthorityNumber); // Stel het nieuwe veld in

                        partyVotesList.add(partyVotes);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        partyVotesRepository.saveAll(partyVotesList);

        return partyVotesList;
    }

    private String extractPostalCode(String reportingUnitIdentifier) {
        String[] parts = reportingUnitIdentifier.split("\\s+");
        String postalCode = "";

        for (String part : parts) {
            if (part.matches("\\d{4}\\s?[A-Z]{2}")) {
                postalCode = part.replaceAll("\\s+", "");
                break;
            }
        }

        return postalCode;
    }

    private String removePostalCode(String reportingUnitIdentifier, String postalCode) {
        return reportingUnitIdentifier.replace(postalCode, "").replaceAll("\\s+", "").trim();
    }
}
