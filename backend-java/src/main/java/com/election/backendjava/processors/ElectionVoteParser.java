package com.election.backendjava.processors;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ElectionVoteParser {
    public static void main(String[] args) {
        try {
            // Setup StAX reader
            XMLInputFactory factory = XMLInputFactory.newInstance();
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Jayso\\IdeaProjects\\wiipuujaamee42\\backend-java\\src\\main\\resources\\data\\Telling_TK2023_gemeente_Ameland.eml.xml"); // Update with the file path
            XMLStreamReader reader = factory.createXMLStreamReader(fileInputStream);

            boolean insideTotalVotes = false;
            String currentAffiliationId = null;
            String candidateId = null;
            String validVotes = null;

            // Iterate through XML elements
            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamReader.START_ELEMENT:
                        String elementName = reader.getLocalName();

                        // Check if we enter TotalVotes
                        if (elementName.equals("TotalVotes")) {
                            insideTotalVotes = true;
                        }

                        // Parse AffiliationIdentifier Id
                        if (insideTotalVotes && elementName.equals("AffiliationIdentifier")) {
                            currentAffiliationId = reader.getAttributeValue(null, "Id");
                        }

                        break;

                    case XMLStreamReader.END_ELEMENT:
                        elementName = reader.getLocalName();

                        if (insideTotalVotes && elementName.equals("Selection")) {
                            System.out.println("AffiliationId: " + (currentAffiliationId != null ? currentAffiliationId : "N/A"));
                            System.out.println("---");

                            // Reset candidate and votes after printing
                            candidateId = null;
                            validVotes = null;
                        }

                        // Check if we exit TotalVotes
                        if (elementName.equals("TotalVotes")) {
                            insideTotalVotes = false;
                            currentAffiliationId = null;
                        }
                        break;
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
