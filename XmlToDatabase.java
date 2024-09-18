import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlToDatabase {

    // Database details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/electiondb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "PASSword123";

    public static void main(String[] args) {
        File xmlFile = new File( "Telling_TK2023_kieskring_Zwolle.eml.xml");
        readAndStoreXmlData(xmlFile);
    }

    private static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static void readAndStoreXmlData(File xmlFile) {
        int rowCount = 0;

        try {
            // Parse XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            // Normalize = For a consistant dom structure
            doc.getDocumentElement().normalize();

            // Extract data from XML
            NodeList selections = doc.getElementsByTagName("Selection");
            try (Connection conn = connectToDatabase()) {
                String insertQuery = "INSERT INTO election_results_zwolle (candidate_id, party, valid_votes) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                    for (int i = 0; i < selections.getLength(); i++) {
                        Element selection = (Element) selections.item(i);
                        String candidateId = "";
                        String party = "";
                        String validVotes = "";

                        // Extract candidateId
                        NodeList candidateIdNodes = selection.getElementsByTagName("CandidateIdentifier");
                        if (candidateIdNodes.getLength() > 0) {
                            candidateId = candidateIdNodes.item(0).getAttributes().getNamedItem("Id").getTextContent();
                        }

                        // Extract party
                        NodeList partyNodes = selection.getElementsByTagName("AffiliationIdentifier");
                        if (partyNodes.getLength() > 0) {
                            party = partyNodes.item(0).getTextContent();
                            System.out.print(party);
                        }

                        // Extract validVotes
                        NodeList validVotesNodes = selection.getElementsByTagName("ValidVotes");
                        if (validVotesNodes.getLength() > 0) {
                            validVotes = validVotesNodes.item(0).getTextContent();
                        }

                        // Insert into database
                        stmt.setString(1, candidateId);
                        stmt.setString(2, party);
                        stmt.setString(3, validVotes);
                        stmt.executeUpdate();

                        rowCount++;
                    }
                }
            }
            System.out.println(rowCount + " rows inserted into the database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
