package com.election.backendjava.controllers;

import com.election.backendjava.entities.CandidateVotes;
import com.election.backendjava.services.CandidateVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate-votes")
public class CandidateVotesController {

    @Autowired
    private CandidateVotesService candidateVotesService;

    @PostMapping("/save")
    public CandidateVotes saveCandidateVotes(@RequestBody CandidateVotes candidateVotes) {
        return candidateVotesService.saveCandidateVotes(candidateVotes);
    }


}