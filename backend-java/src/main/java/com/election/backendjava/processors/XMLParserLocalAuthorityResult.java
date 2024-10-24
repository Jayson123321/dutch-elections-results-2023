package com.election.backendjava.processors;

import com.election.backendjava.entities.AuthorityTotalVote;
import com.election.backendjava.repositories.ResultLocalAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

@Service
public class XMLParserLocalAuthorityResult {

    @Autowired
    private ResultLocalAuthorityRepository ResultLocalAuthorityRepository;

    public void parseAndSave(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            // Get value of 'Id' attribute of 'AuthorityIdentifier' element
            String authorityIdentifier = doc.getElementsByTagName("AuthorityIdentifier").item(0).getAttributes().getNamedItem("Id").getNodeValue();
            // Get all 'TotalVotes' elements
            NodeList totalVotesList = doc.getElementsByTagName("TotalVotes");

            for (int i = 0; i < totalVotesList.getLength(); i++) {
                Node totalVotesNode = totalVotesList.item(i);
                if (totalVotesNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element totalVotesElement = (Element) totalVotesNode;
                    NodeList selectionNodes = totalVotesElement.getElementsByTagName("Selection");

                    for (int j = 0; j < selectionNodes.getLength(); j++) {
                        Node selectionNode = selectionNodes.item(j);
                        if (selectionNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element selectionElement = (Element) selectionNode;
                            if (selectionElement.getElementsByTagName("AffiliationIdentifier").getLength() > 0) {
                                String affiliationIdentifier = selectionElement.getElementsByTagName("AffiliationIdentifier").item(0).getAttributes().getNamedItem("Id").getNodeValue();
                                int validVotes = Integer.parseInt(selectionElement.getElementsByTagName("ValidVotes").item(0).getTextContent());
                                // Save to database
                                AuthorityTotalVote authorityTotalVote = new AuthorityTotalVote();
                                authorityTotalVote.setAuthorityIdentifier(authorityIdentifier);
                                authorityTotalVote.setAffiliationIdentifier(affiliationIdentifier);
                                authorityTotalVote.setValidVotes(validVotes);
                                ResultLocalAuthorityRepository.save(authorityTotalVote);

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}