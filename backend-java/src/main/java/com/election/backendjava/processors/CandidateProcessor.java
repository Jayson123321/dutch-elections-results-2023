package com.election.backendjava.processors;

import com.election.backendjava.entities.Candidate;
import com.election.backendjava.repositories.CandidateRepository;
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
public class CandidateProcessor {

    @Autowired
    private CandidateRepository candidateRepository;

    @Transactional
    public void processXML(String filePath) {
        try {
            // XML bestand inlezen
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            // Ophalen van alle affiliaties
            NodeList affiliationNodes = document.getElementsByTagName("Affiliation");

            for (int i = 0; i < affiliationNodes.getLength(); i++) {
                Element affiliationElement = (Element) affiliationNodes.item(i);

                // Extracting affiliation id
                String affiliationId = affiliationElement
                        .getElementsByTagName("AffiliationIdentifier")
                        .item(0)
                        .getAttributes()
                        .getNamedItem("Id")
                        .getNodeValue();

                // Skip affiliations 1, 2, 3, 4, 5, and 6
                int affiliationIdInt = Integer.parseInt(affiliationId);
                if (affiliationIdInt >= 1 && affiliationIdInt <= 6) {
                    continue; // Skip these specific affiliations
                }

                // Ophalen van alle kandidaten binnen deze affiliatie
                NodeList candidateNodes = affiliationElement.getElementsByTagName("Candidate");

                for (int j = 0; j < candidateNodes.getLength(); j++) {
                    Element candidateElement = (Element) candidateNodes.item(j);

                    // Extracting candidate identifier
                    String candidateIdentifier = candidateElement
                            .getElementsByTagName("CandidateIdentifier")
                            .item(0)
                            .getAttributes()
                            .getNamedItem("Id")
                            .getNodeValue();

                    // Extracting candidate name
                    String firstName = "";
                    NodeList firstNameList = candidateElement.getElementsByTagName("xnl:FirstName");
                    if (firstNameList != null && firstNameList.getLength() > 0) {
                        firstName = firstNameList.item(0).getTextContent();
                    }

                    String lastName = "";
                    NodeList lastNameList = candidateElement.getElementsByTagName("xnl:LastName");
                    if (lastNameList != null && lastNameList.getLength() > 0) {
                        lastName = lastNameList.item(0).getTextContent();
                    }

                    String candidateName = firstName + " " + lastName;

                    // Extracting gender (if present)
                    String gender = "";
                    NodeList genderList = candidateElement.getElementsByTagName("Gender");
                    if (genderList != null && genderList.getLength() > 0) {
                        gender = genderList.item(0).getTextContent();
                    }

                    // Extracting qualifying address
                    String qualifyingAddress = "";
                    NodeList addressList = candidateElement.getElementsByTagName("xal:LocalityName");
                    if (addressList != null && addressList.getLength() > 0) {
                        qualifyingAddress = addressList.item(0).getTextContent();
                    }

                    // Maak een nieuw Candidate object
                    Candidate candidate = new Candidate(candidateIdentifier, candidateName, gender, qualifyingAddress, affiliationId);

                    // Sla de kandidaat op in de database
                    candidateRepository.save(candidate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
