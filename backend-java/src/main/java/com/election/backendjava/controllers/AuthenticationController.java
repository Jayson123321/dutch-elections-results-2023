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
            System.out.println(user.getUsername());
            Long userid = authenticationService.login(user);

            String token = jwtUtil.generateToken(String.valueOf(userid));

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("userid", String.valueOf(userid));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
