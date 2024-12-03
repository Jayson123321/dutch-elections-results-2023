package com.election.backendjava.controllers;

import com.election.backendjava.entities.CandidateAuthorityVotes;
import com.election.backendjava.repositories.CandidateAuthorityVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-authority-votes")
public class CandidateAuthorityVotesController {

    @Autowired
    private CandidateAuthorityVotesRepository candidateAuthorityVotesRepository;

    @GetMapping("/{authorityIdentifier}")
    public List<CandidateAuthorityVotes> getAuthorityVotesByAuthorityIdentifier(@PathVariable("authorityIdentifier") String authorityIdentifier) {
        return candidateAuthorityVotesRepository.findByAuthorityIdentifier(authorityIdentifier);
    }

    @GetMapping("/all")
    public List<CandidateAuthorityVotes> getAllVotes() {
        return candidateAuthorityVotesRepository.findAll();
    }
}