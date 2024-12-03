package com.election.backendjava.controllers;

import com.election.backendjava.models.Account;
import com.election.backendjava.repositories.AccountsRepository;
import com.election.backendjava.config.ApiConfig;
import com.election.backendjava.security.JWToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.NotAcceptableException;
import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AccountsRepository accountsRepo;

    @Autowired
    private ApiConfig apiConfig;

    @PostMapping(path = "/login")
    public ResponseEntity<Account> authenticateAccount(@RequestBody ObjectNode signInInfo) {

        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        List<Account> accounts = accountsRepo.findByQuery("Accounts_find_by_email", email);
        Account account = accounts.size() == 1 ? accounts.get(0) : null;

        if (account == null || !account.verifyPassword(password)) {
            throw new NotAcceptableException("Cannot authenticate account with email=" + email);
        }

        // Issue a token for the account, valid for some time
        JWToken jwToken = new JWToken(account.getCallName(), account.getId());
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(), this.apiConfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(account);
    }
}