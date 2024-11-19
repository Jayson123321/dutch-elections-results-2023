package com.election.backendjava.services;

import com.election.backendjava.entities.CandidateVotes;
import com.election.backendjava.repositories.CandidateVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateVotesService {

    @Autowired
    private CandidateVotesRepository candidateVotesRepository;


    public Optional<CandidateVotes> findCandidateVotesById(long id) {
        return candidateVotesRepository.findById(id);
    }

    public CandidateVotes saveCandidateVotes(CandidateVotes candidateVotes) {
        return candidateVotesRepository.save(candidateVotes);
    }
}