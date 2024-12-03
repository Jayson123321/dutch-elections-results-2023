package com.election.backendjava.controllers;
import com.election.backendjava.entities.AuthorityTotalVote;
import com.election.backendjava.repositories.ResultLocalAuthorityRepository;
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
    private ResultLocalAuthorityRepository resultLocalAuthorityRepository;
    // Get all the votes for a specific authority
    @GetMapping("{authorityId}")
    public List<AuthorityTotalVote> getAuthorityTotalVotes(@PathVariable String authorityId) {
        return resultLocalAuthorityRepository.findByAuthorityId(authorityId);
    }
    // Get all the votes for a specific authority sorted by votes
    @GetMapping("/sortedByVotes/{authorityId}")
    public List<AuthorityTotalVote> getAuthorityTotalVotesSortedByVotes(@PathVariable String authorityId) {
        return resultLocalAuthorityRepository.findByAuthorityIdOrderByValidVotesDesc(authorityId);
    }
}
