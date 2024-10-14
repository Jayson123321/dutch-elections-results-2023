package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    // Endpoint om kandidaten op basis van affiliationId op te halen
    @GetMapping
    public List<Candidate> getCandidatesByAffiliationId(@RequestParam String affiliationId) {
        return candidateService.getCandidatesByAffiliationId(affiliationId);
    }

    // Endpoint om alle kandidaten op te halen
    @GetMapping("/all")
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }
}
