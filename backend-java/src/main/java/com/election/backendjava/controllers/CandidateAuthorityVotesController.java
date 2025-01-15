package com.election.backendjava.controllers;

import com.election.backendjava.entities.CandidateAuthorityVotes;
import com.election.backendjava.services.CandidateAuthorityVotesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
@RestController
@RequestMapping("/api/candidate-authority-votes")
public class CandidateAuthorityVotesController {

    @Autowired
    private CandidateAuthorityVotesService candidateAuthorityVotesService;

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<CandidateAuthorityVotes>> getVotesByCandidate(@PathVariable Long candidateId) {
        List<CandidateAuthorityVotes> votes = candidateAuthorityVotesService.getVotesByCandidate(candidateId);
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/{candidateId}/total-valid-votes")
    public int getTotalValidVotesByCandidate(@PathVariable Long candidateId) {
        return candidateAuthorityVotesService.calculateTotalValidVotesByCandidate(candidateId);
    }
}
