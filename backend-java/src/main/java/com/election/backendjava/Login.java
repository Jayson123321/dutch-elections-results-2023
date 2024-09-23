package com.election.backendjava;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class Login {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest userCredentials) {
        System.out.println("Received login request:");
        System.out.println("Username: " + userCredentials.getUsername());
        System.out.println("Password: " + userCredentials.getPassword());

        String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();

        if ("admin".equals(username) && "password".equals(password)) {
            return ResponseEntity.ok("{\"message\": \"Login successful\"}");

        } else {
            return ResponseEntity.status(401).body("{\"message\": \"Login failed\"}");
        }
    }
}
