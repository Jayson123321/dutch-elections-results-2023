package com.election.backendjava.services;

import com.election.backendjava.entities.Candidate;
import com.election.backendjava.entities.CandidateVotes;
import com.election.backendjava.repositories.CandidateRepository;
import com.election.backendjava.repositories.CandidateVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private CandidateVotesRepository candidateVotesRepository;

    public Candidate findCandidateById(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }

    public List<Candidate> searchCandidates(String searchTerm) {
        List<Candidate> allCandidates = candidateRepository.findAll();
        String searchTermLower = searchTerm.toLowerCase();
        return allCandidates.stream()
                .filter(candidate -> candidate.getCandidateName().toLowerCase().contains(searchTermLower) ||
                        candidate.getQualifyingAddress().toLowerCase().contains(searchTermLower) )
                .collect(Collectors.toList());
    }
}
