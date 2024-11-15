package com.election.backendjava;

import com.election.backendjava.processors.ManagingAuthorityXmlParser;
import com.election.backendjava.processors.ReportingUnitXmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private ManagingAuthorityXmlParser managingAuthorityXmlParser;

    @Autowired
    private ReportingUnitXmlParser reportingUnitXmlParser;

    @Autowired
    private com.election.backendjava.processors.PartyVotesParser PartyVotesParser;


    @Override
    public void run(String... args) throws Exception {
        String directoryPath = "C:\\Users\\Jayso\\IdeaProjects\\wiipuujaamee42";

        File folder = new File(directoryPath);
        // Checkt of de folder bestaat en of het een directory is
        if (folder.exists() && folder.isDirectory()) {
            // Filtert alle XML-bestanden uit de folder
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

            if (files != null) {
                int fileCount = 0;

                for (File file : files) {
                    System.out.println("Verwerken van bestand: " + file.getName());

                    PartyVotesParser.parseXML(file.getAbsolutePath());
                    fileCount++;
                }

                System.out.println(fileCount + " bestanden succesvol verwerkt.");
            } else {
                System.out.println("Geen XML-bestanden gevonden in de map.");
            }
        }
    }
}
