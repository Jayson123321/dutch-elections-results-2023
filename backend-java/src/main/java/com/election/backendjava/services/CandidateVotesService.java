package com.election.backendjava.services;

import com.election.backendjava.entities.CandidateVotes;
import com.election.backendjava.repositories.CandidateVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateVotesService {

    @Autowired
    private CandidateVotesRepository candidateVotesRepository;

    public CandidateVotes saveCandidateVotes(CandidateVotes candidateVotes) {
        return candidateVotesRepository.save(candidateVotes);
    }
}