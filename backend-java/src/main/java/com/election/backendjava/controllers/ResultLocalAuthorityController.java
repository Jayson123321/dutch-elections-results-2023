package com.election.backendjava.controllers;
import com.election.backendjava.entities.AuthorityTotalVote;
import com.election.backendjava.entities.CandidateAuthorityVotes;
import com.election.backendjava.repositories.CandidateAuthorityVotesRepository;
import com.election.backendjava.repositories.ResultLocalAuthorityRepository;
import com.election.backendjava.services.CandidateAuthorityVotesService;
import com.election.backendjava.services.ResultLocalAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/result-local-authority")
public class ResultLocalAuthorityController {

    @Autowired
    private ResultLocalAuthorityService resultLocalAuthorityService;

    @Autowired
    private CandidateAuthorityVotesService CandidateAuthorityVotesService;

    // Get all the votes for a specific authority
    @GetMapping("{authorityId}")
    public List<AuthorityTotalVote> getAuthorityTotalVotes(@PathVariable String authorityId) {
        return resultLocalAuthorityService.findByAuthorityId(authorityId);
    }
    // Get all the votes for a specific authority sorted by votes
    @GetMapping("/sortedByVotes/{authorityId}")
    public List<AuthorityTotalVote> getAuthorityTotalVotesSortedByVotes(@PathVariable String authorityId) {
        return resultLocalAuthorityService.findByAuthorityIdOrderByValidVotesDesc(authorityId);
    }
    @GetMapping("/party/{affiliationId}/authority/{authorityId}")
    public List<CandidateAuthorityVotes> getVotesByAffiliationAndAuthority(@PathVariable Long affiliationId, @PathVariable String authorityId) {
        return CandidateAuthorityVotesService.findByAffiliationIdAndAuthorityIdentifier(affiliationId, authorityId);
    }
    @GetMapping("/party/{affiliationId}/authority/{authorityId}/sortedByVotes")
    public List<CandidateAuthorityVotes> getVotesByAffiliationAndAuthoritySortedByVotes(@PathVariable Long affiliationId, @PathVariable String authorityId) {
        return CandidateAuthorityVotesService.findByAffiliationIdAndAuthorityIdentifierOrderByValidVotesDesc(affiliationId, authorityId);
    }
    @GetMapping("/party/{affiliationId}/authority/{authorityId}/sortedByName")
    public List<CandidateAuthorityVotes> getVotesByAffiliationAndAuthoritySortedByName(@PathVariable Long affiliationId, @PathVariable String authorityId) {
        return CandidateAuthorityVotesService.findByAffiliationIdAndAuthorityIdentifierOrderByCandidateName(affiliationId, authorityId);
    }
}
