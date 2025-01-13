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

    public List<CandidateAuthorityVotes> findByAffiliationIdAndAuthorityIdentifier(Long affiliationId, String authorityId) {
        return candidateAuthorityVotesRepository.findByAffiliationIdAndAuthorityIdentifier(affiliationId, authorityId);
    }
    // sort by votes
    public List<CandidateAuthorityVotes> findByAffiliationIdAndAuthorityIdentifierOrderByValidVotesDesc(Long affiliationId, String authorityId) {
        return candidateAuthorityVotesRepository.findByAffiliationIdAndAuthorityIdentifierOrderByValidVotesDesc(affiliationId, authorityId);
    }
    // sort by name
    public List<CandidateAuthorityVotes> findByAffiliationIdAndAuthorityIdentifierOrderByCandidateName(Long affiliationId, String authorityId) {
        return candidateAuthorityVotesRepository.findByAffiliationIdAndAuthorityIdentifierOrderByCandidateName(affiliationId, authorityId);
    }
    // functie: berekent het totaal aantal geldige stemmen voor een kandidaat
    public int calculateTotalValidVotesByCandidate(Long candidateId) {
        List<CandidateAuthorityVotes> votesList = candidateAuthorityVotesRepository.findByCandidateId(candidateId);
        return votesList.stream().mapToInt(CandidateAuthorityVotes::getValidVotes).sum();
    }
}