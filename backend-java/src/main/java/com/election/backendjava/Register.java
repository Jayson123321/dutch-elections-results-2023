package com.election.backendjava;

import com.election.backendjava.entities.User;
import com.election.backendjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Register {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody User userCredentials) {
        System.out.println("Received registration request:");
        System.out.println("Username: " + userCredentials.getUsername());
        System.out.println("Password: " + userCredentials.getPassword());
        System.out.println("Email: " + userCredentials.getEmail());

        // Controleert of de gebruiker al bestaat
        if (userRepository.findByUsername(userCredentials.getUsername()) != null) {
            return ResponseEntity.badRequest().body("{\"message\": \"Username already exists\"}");
        }

        if (userRepository.findByEmail(userCredentials.getEmail()) != null) {
            return ResponseEntity.badRequest().body("{\"message\": \"Email already exists\"}");
        }

        // Maak een nieuwe gebruiker aan
        User newUser = new User();
        newUser.setUsername(userCredentials.getUsername());
        newUser.setPassword(userCredentials.getPassword());
        newUser.setEmail(userCredentials.getEmail());

        // Sla de gebruiker op in de database
        userRepository.save(newUser);

        return ResponseEntity.ok("{\"message\": \"Registration successful\"}");
    }
}
