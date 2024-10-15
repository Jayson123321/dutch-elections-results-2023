package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class TotalVoteController {

    private final TotalVoteRepository totalVoteRepository;

    @Autowired
    public TotalVoteController(TotalVoteRepository totalVoteRepository) {
        this.totalVoteRepository = totalVoteRepository;
    }

    @GetMapping
    public List<TotalVote> getAllTotalVotes() {
        return totalVoteRepository.findAll();
    }
}