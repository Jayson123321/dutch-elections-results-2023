package com.election.backendjava.services;

import com.election.backendjava.entities.AuthorityTotalVote;
import com.election.backendjava.repositories.CandidateAuthorityVotesRepository;
import com.election.backendjava.repositories.ResultLocalAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultLocalAuthorityService {

    @Autowired
    private ResultLocalAuthorityRepository resultLocalAuthorityRepository;


    public List<AuthorityTotalVote> findByAuthorityId(String authorityId) {
        return resultLocalAuthorityRepository.findByAuthorityId(authorityId);
    }
    // functie: soteert op validVotes
    public List<AuthorityTotalVote> findByAuthorityIdOrderByValidVotesDesc(String authorityId) {
        return resultLocalAuthorityRepository.findByAuthorityIdOrderByValidVotesDesc(authorityId);
    }
    // functie: soteert op naam
    public List<AuthorityTotalVote> findByAuthorityIdOrderByAffiliation(String authorityId) {
        return resultLocalAuthorityRepository.findByAuthorityIdOrderByAffiliation(authorityId);
    }
    // functie: berekent het totaal aantal stemmen voor een gemeente
    public int calculateTotalValidVotes(String authorityId) {
        List<AuthorityTotalVote> authorityTotalVotesList = resultLocalAuthorityRepository.findByAuthorityId(authorityId);
        return authorityTotalVotesList.stream().mapToInt(AuthorityTotalVote::getValidVotes).sum();
    }
}