package com.election.backendjava.controllers;

import com.election.backendjava.dto.JWToken;
import com.election.backendjava.dto.RegisterRequest;
import com.election.backendjava.entities.User;
import com.election.backendjava.repositories.UserRepository;
import com.election.backendjava.APIconfig;
import com.election.backendjava.services.AuthService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private APIconfig apiConfig;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity <Void> register (@RequestBody RegisterRequest registerRequest){
        authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}