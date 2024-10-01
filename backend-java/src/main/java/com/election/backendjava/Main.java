package com.election.backendjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        // Zorg ervoor dat dit een niet-web applicatie is
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}
