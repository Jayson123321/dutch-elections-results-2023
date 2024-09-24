package com.election.backendjava;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")

public class Register {

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody RegistrationRequest userCredentials) {
        System.out.println("Received registration request:");
        System.out.println("Username: " + userCredentials.getUsername());
        System.out.println("Password: " + userCredentials.getPassword());
        System.out.println("Email: " + userCredentials.getEmail());

        String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
        String email = userCredentials.getEmail();
        return ResponseEntity.ok("{\"message\": \"Registration successful\"}");
    }
}


