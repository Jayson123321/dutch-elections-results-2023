package com.election.backendjava;

import com.election.backendjava.processors.ElectionVoteParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ElectionVoteParser electionVoteParser;

    @Override
    public void run(String... args) throws Exception {
        String directoryPath = "C:\\Users\\Jayso\\IdeaProjects\\wiipuujaamee42\\backend-java\\src\\main\\resources\\data";
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    electionVoteParser.parseAndSaveVotes(file.getAbsolutePath());
                }
            }
        } else {
            System.out.println("Directory does not exist or is not a directory.");
        }
    }
}