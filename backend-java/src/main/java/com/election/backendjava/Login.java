package com.election.backendjava;

import com.election.backendjava.entities.User;
import com.election.backendjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class Login {

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User userCredentials) {
        // Zoek de gebruiker op basis van gebruikersnaam en wachtwoord
        User userFromDb = userRepository.findByUsernameAndPassword(userCredentials.getUsername(), userCredentials.getPassword());

        if (userFromDb != null) {
            // Als de gebruiker bestaat en het wachtwoord correct is
            return ResponseEntity.ok("{\"message\": \"Login successful\"}");
        } else {
            // Als de gebruiker niet bestaat of het wachtwoord onjuist is
            return ResponseEntity.status(401).body("{\"message\": \"Login failed: Incorrect username or password\"}");
        }
    }
}