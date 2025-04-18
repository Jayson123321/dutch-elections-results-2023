package com.election.backendjava.controllers;

import com.election.backendjava.entities.User;
import com.election.backendjava.services.AuthenticationService;
import com.election.backendjava.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            authenticationService.register(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Ensure the username is correctly retrieved from the User object
            String username = user.getUsername();
            if (username == null || username.isEmpty()) {
                throw new IllegalArgumentException("Username is missing");
            }

            System.out.println(username);
            Long userid = authenticationService.login(user);

            String token = jwtUtil.generateToken(String.valueOf(userid));

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("userid", String.valueOf(userid));
            response.put("username", username); // Add the username to the response

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}