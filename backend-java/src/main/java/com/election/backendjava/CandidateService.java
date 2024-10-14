package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    // Haal kandidaten op basis van affiliationId
    public List<Candidate> getCandidatesByAffiliationId(String affiliationId) {
        return candidateRepository.findByAffiliationId(affiliationId);
    }

    // Haal alle kandidaten op
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
}
