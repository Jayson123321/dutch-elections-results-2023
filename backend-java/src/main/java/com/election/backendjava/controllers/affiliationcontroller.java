package com.election.backendjava.controllers;

import com.election.backendjava.entities.Affiliation;
import com.election.backendjava.repositories.AffiliationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/affiliations")
public class affiliationcontroller {

    @Autowired
    private AffiliationRepository affiliationRepository;

    @GetMapping
    public List<Affiliation> getAllAffiliations() {
        return affiliationRepository.findAll();
    }
}