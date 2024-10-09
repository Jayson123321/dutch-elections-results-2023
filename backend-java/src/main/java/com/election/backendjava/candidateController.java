package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/affiliations")
public class candidateController {

    @Autowired
    private candidateService candidateService;

    @GetMapping("/{affiliationId}/candidates")
    public List<Candidate> getCandidatesByAffiliationId(@PathVariable Long affiliationId) {
        return candidateService.getCandidatesByAffiliationId(affiliationId);
    }
}