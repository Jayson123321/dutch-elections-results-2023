package com.election.backendjava.services;

import com.election.backendjava.entities.CandidateAuthorityVotes;
import com.election.backendjava.repositories.CandidateAuthorityVotesRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class CandidateAuthorityVotesService {

    @Autowired
    private CandidateAuthorityVotesRepository candidateAuthorityVotesRepository;

    public List<CandidateAuthorityVotes> getVotesByCandidate(Long candidateId) {
        return candidateAuthorityVotesRepository.findByCandidateId(candidateId);
    }
}
