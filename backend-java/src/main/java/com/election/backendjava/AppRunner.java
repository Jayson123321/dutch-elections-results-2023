package com.election.backendjava;

import com.election.backendjava.processors.XMLParserLocalAuthorityResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private XMLParserLocalAuthorityResult xmlParserService;

    @Override
    public void run(String... args) throws Exception {
        xmlParserService.parseAndSave("C:\\Users\\kaylinbon\\IdeaProjects\\wiipuujaamee42\\backend-java\\src\\main\\java\\com\\election\\backendjava\\Telling_TK2023_gemeente_Alblasserdam.eml.xml");    }
}