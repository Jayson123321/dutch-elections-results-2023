package com.election.backendjava.controllers;

import com.election.backendjava.entities.User;
import com.election.backendjava.repositories.UserRepository;
import com.election.backendjava.APIconfig;
import com.election.backendjava.utils.JWToken;
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
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private APIconfig apiConfig;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/login")
    public ResponseEntity<User> authenticateUser(@RequestBody ObjectNode signInInfo) {

        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        User user = userRepository.findByEmail(email);

        if (user == null || !user.verifyPassword(password)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Cannot authenticate user with email=" + email);
        }

        // Issue a token for the user, valid for some time
        JWToken jwToken = new JWToken(user.getUsername(), user.getId(), "someAdditionalParameter");
        String tokenString = jwToken.encode(apiConfig.getIssuer(), apiConfig.getPassphrase(), apiConfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(user);
    }
}