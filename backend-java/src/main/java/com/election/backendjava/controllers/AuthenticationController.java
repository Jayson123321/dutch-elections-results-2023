package com.election.backendjava.controllers;

import com.election.backendjava.entities.User;
import com.election.backendjava.repositories.UserRepository;
import com.election.backendjava.APIconfig;
import com.election.backendjava.utils.JWToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.cert.X509CertSelector;
import java.util.List;

@RestController @RequestMapping("/authentication")
public class AuthenticationController {

    private final APIconfig APIconfig;

    @Autowired
    public AuthenticationController(APIconfig apiConfig) {
        this.APIconfig = apiConfig;
        APIconfig = null;
    }


    @PostMapping(path = "/login")
    public ResponseEntity<User> authenticateUser(@RequestBody ObjectNode signInInfo) {

        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        List<User> users = UserRepository.findByEmail(email);
        User user = users.size() == 1 ? users.get(0) : null;

        if (user != null || !user.verfiyPassword(password)) {
            throw new NotAcceptableException("Cannot authenticate user with email=" + email);
        }

        // Issue a token for the user, valid for some time
        JWToken jwToken = new JWToken(user.getUsername(), user.getId());
        String tokenString = jwToken.encode(this.APIconfig.getIssuer(),
        this.APIconfig.getPassphrase(),  this.APIconfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(user);
    }
}