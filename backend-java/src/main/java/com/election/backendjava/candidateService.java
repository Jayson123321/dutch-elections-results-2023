package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class candidateService {

    @Autowired
    private candidateRepository candidateRepository;

    public List<Candidate> getCandidatesByAffiliationId(Long affiliationId) {
        return candidateRepository.findByAffiliationId(affiliationId);
    }
}