package com.election.backendjava.controllers;

import com.election.backendjava.dto.TotalVoteWithPercentageDTO;
import com.election.backendjava.entities.TotalVote;
import com.election.backendjava.services.TotalVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class TotalVoteController {

    private final TotalVoteService totalVoteService;

    @Autowired
    public TotalVoteController(TotalVoteService totalVoteService) {
        this.totalVoteService = totalVoteService;
    }

    @GetMapping("/sortedByName")
    public List<TotalVote> getAllVotesSortedByAffiliationName() {
        return totalVoteService.getAllVotesSortedByAffiliationName();
    }

    @GetMapping("/sortedByVotes")
    public List<TotalVote> getAllVotesSortedByTotalVotes() {
        return totalVoteService.getAllVotesSortedByTotalVotes();
    }

    @GetMapping
    public List<TotalVoteWithPercentageDTO> getAllTotalVotes() {
        return totalVoteService.getAllTotalVotesWithPercentage();
    }
}
