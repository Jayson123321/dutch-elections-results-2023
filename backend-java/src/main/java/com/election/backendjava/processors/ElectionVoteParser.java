package com.election.backendjava.processors;

import com.election.backendjava.entities.CandidateAuthorityVotes;
import com.election.backendjava.repositories.CandidateAuthorityVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
public class ElectionVoteParser {

    @Autowired
    private CandidateAuthorityVotesRepository candidateAuthorityVotesRepository;

    public void parseAndSaveVotes(String filePath) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            FileInputStream fileInputStream = new FileInputStream(filePath);
            XMLStreamReader reader = factory.createXMLStreamReader(fileInputStream);

            String currentAuthorityId = null;
            boolean insideTotalVotes = false;
            String currentAffiliationId = null;
            String candidateId = null;
            String validVotes = null;

            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamReader.START_ELEMENT:
                        String elementName = reader.getLocalName();

                        // Parse ManagingAuthority -> AuthorityIdentifier
                        if (!insideTotalVotes && elementName.equals("AuthorityIdentifier")) {
                            currentAuthorityId = reader.getAttributeValue(null, "Id");
                        }

                        // Enter TotalVotes section
                        if (elementName.equals("TotalVotes")) {
                            insideTotalVotes = true;
                        }

                        // Parse AffiliationIdentifier Id
                        if (insideTotalVotes && elementName.equals("AffiliationIdentifier")) {
                            currentAffiliationId = reader.getAttributeValue(null, "Id");
                        }

                        // Parse CandidateIdentifier Id
                        if (insideTotalVotes && elementName.equals("CandidateIdentifier")) {
                            candidateId = reader.getAttributeValue(null, "Id");
                        }

                        // Parse ValidVotes
                        if (insideTotalVotes && elementName.equals("ValidVotes")) {
                            validVotes = reader.getElementText();
                        }
                        break;

                    case XMLStreamReader.END_ELEMENT:
                        elementName = reader.getLocalName();

                        if (insideTotalVotes && elementName.equals("Selection")) {
                            if (currentAuthorityId != null && currentAffiliationId != null && candidateId != null && validVotes != null) {
                                // Save CandidateAuthorityVotes
                                CandidateAuthorityVotes votes = new CandidateAuthorityVotes();
                                votes.setAuthorityIdentifier(currentAuthorityId);
                                votes.setAffiliationIdentifier(currentAffiliationId);
                                votes.setCandidateIdentifier(candidateId);
                                votes.setValidVotes(Integer.parseInt(validVotes));
                                candidateAuthorityVotesRepository.save(votes);
                            }

                            // Reset candidate and votes
                            candidateId = null;
                            validVotes = null;
                        }

                        if (elementName.equals("TotalVotes")) {
                            insideTotalVotes = false;
                            currentAffiliationId = null;
                        }
                        break;
                }
            }

            reader.close();
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}