package com.election.backendjava.controllers;

import com.election.backendjava.entities.CandidateVotes;
import com.election.backendjava.services.CandidateVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-votes")
public class CandidateVotesController {

    @Autowired
    private CandidateVotesService candidateVotesService;

    @PostMapping("/save")
    public CandidateVotes saveCandidateVotes(@RequestBody CandidateVotes candidateVotes) {
        return candidateVotesService.saveCandidateVotes(candidateVotes);
    }

    @GetMapping("/votes/{id}")
    public ResponseEntity<CandidateVotes> findCandidateVotesById(@PathVariable long id) {
        return candidateVotesService.findCandidateVotesById(id)
                .map(ResponseEntity::ok)         // Als het record wordt gevonden, retourneer het met status 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Als niet gevonden, retourneer 404 Not Found
    }

}
