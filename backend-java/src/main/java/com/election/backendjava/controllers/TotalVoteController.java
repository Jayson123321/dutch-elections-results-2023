package com.election.backendjava.controllers;

import com.election.backendjava.dto.TotalVoteWithPercentageDTO;
import com.election.backendjava.entities.TotalVote;
import com.election.backendjava.repositories.TotalVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/results")
public class TotalVoteController {

    private final TotalVoteRepository totalVoteRepository;

    @Autowired
    public TotalVoteController(TotalVoteRepository totalVoteRepository) {
        this.totalVoteRepository = totalVoteRepository;
    }

    @GetMapping("/sortedByName")
    public List<TotalVote> getAllVotesSortedByAffiliationName() {
        return totalVoteRepository.findAll().stream()
                .sorted((a, b) -> a.getAffiliationName().compareToIgnoreCase(b.getAffiliationName()))
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<TotalVoteWithPercentageDTO> getAllTotalVotes() {
        List<TotalVote> totalVotes = totalVoteRepository.findAll();

        // Bereken het totaal aantal stemmen
        int sumVotes = totalVotes.stream().mapToInt(TotalVote::getTotalVotes).sum();

        // Maak een lijst van DTO's met percentages
        List<TotalVoteWithPercentageDTO> result = new ArrayList<>();
        for (TotalVote vote : totalVotes) {
            double percentage = sumVotes > 0 ? (double) vote.getTotalVotes() / sumVotes * 100 : 0;
            result.add(new TotalVoteWithPercentageDTO(vote.getAffiliationName(), vote.getTotalVotes(), percentage));
        }

        return result;
    }

}

//    public List<TotalVote> getAllTotalVotes() {
//        return totalVoteRepository.findAll();
//    }