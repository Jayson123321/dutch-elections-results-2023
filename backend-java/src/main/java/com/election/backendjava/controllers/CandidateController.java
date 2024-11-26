// CandidateController.java
package com.election.backendjava.controllers;

import com.election.backendjava.entities.Candidate;
import com.election.backendjava.entities.CandidateVotes;
import com.election.backendjava.services.CandidateService;
import com.election.backendjava.services.CandidateVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;
    @Autowired
    private CandidateVotesService candidateVotesService;

    @GetMapping
    public List<Candidate> getCandidatesByAffiliationId(@RequestParam String affiliationId) {
        return candidateService.getCandidatesByAffiliationId(affiliationId);
    }

    @GetMapping("/all")
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        Candidate candidate = candidateService.findCandidateById(id);
        if (candidate != null) {
            return ResponseEntity.ok(candidate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }





}