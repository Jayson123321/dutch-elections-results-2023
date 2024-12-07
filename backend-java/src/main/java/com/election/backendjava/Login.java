package com.election.backendjava;

import com.election.backendjava.entities.User;
import com.election.backendjava.repositories.UserRepository;
import com.election.backendjava.utils.JWToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Login {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private APIconfig apiConfig;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User userCredentials) {
        User userFromDb = userRepository.findByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword());

        if (userFromDb != null) {
            JWToken jwToken = new JWToken(userFromDb.getUsername(), userFromDb.getId(), userFromDb.getRole());
            String tokenString = jwToken.encode(apiConfig.getIssuer(), apiConfig.getPassphrase(), apiConfig.getTokenDurationOfValidity());

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                    .body("{\"message\": \"Login successful\"}");
        } else {
            return ResponseEntity.status(401).body("{\"message\": \"Login failed: Incorrect email or password\"}");
        }
    }
}