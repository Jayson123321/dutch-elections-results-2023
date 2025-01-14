package com.election.backendjava.controllers;

import com.election.backendjava.dto.TotalVoteWithPercentageDTO;
import com.election.backendjava.entities.TotalVote;
import com.election.backendjava.services.TotalVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class to handle API endpoints related to TotalVotes.
 * Delegates the business logic to the TotalVoteService.
 */
@RestController
@RequestMapping("/api/results")
public class TotalVoteController {

    private final TotalVoteService totalVoteService;

    @Autowired
    public TotalVoteController(TotalVoteService totalVoteService) {
        this.totalVoteService = totalVoteService;
    }

    /**
     * Endpoint to fetch all votes sorted by affiliation name.
     * @return List of TotalVote sorted alphabetically by affiliation name.
     */
    @GetMapping("/sortedByName")
    public List<TotalVote> getAllVotesSortedByAffiliationName() {
        return totalVoteService.getAllVotesSortedByAffiliationName();
    }

    /**
     * Endpoint to fetch all votes sorted by the number of total votes in descending order.
     * @return List of TotalVote sorted by vote count.
     */
    @GetMapping("/sortedByVotes")
    public List<TotalVote> getAllVotesSortedByTotalVotes() {
        return totalVoteService.getAllVotesSortedByTotalVotes();
    }

    /**
     * Endpoint to fetch all votes along with their percentages.
     * @return List of TotalVoteWithPercentageDTO containing vote data and percentages.
     */
    @GetMapping
    public List<TotalVoteWithPercentageDTO> getAllTotalVotes() {
        return totalVoteService.getAllTotalVotesWithPercentage();
    }
}
