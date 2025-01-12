package com.election.backendjava.services;

import com.election.backendjava.dto.TotalVoteWithPercentageDTO;
import com.election.backendjava.entities.TotalVote;
import com.election.backendjava.repositories.TotalVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TotalVoteService {

    private final TotalVoteRepository totalVoteRepository;

    @Autowired
    public TotalVoteService(TotalVoteRepository totalVoteRepository) {
        this.totalVoteRepository = totalVoteRepository;
    }

    public List<TotalVote> getAllVotesSortedByAffiliationName() {
        return totalVoteRepository.findAll().stream()
                .sorted((a, b) -> a.getAffiliationName().compareToIgnoreCase(b.getAffiliationName()))
                .collect(Collectors.toList());
    }

    public List<TotalVote> getAllVotesSortedByTotalVotes() {
        return totalVoteRepository.findAll().stream()
                .sorted((a, b) -> b.getTotalVotes().compareTo(a.getTotalVotes()))
                .collect(Collectors.toList());
    }

    public List<TotalVoteWithPercentageDTO> getAllTotalVotesWithPercentage() {
        List<TotalVote> totalVotes = totalVoteRepository.findAll();
        int sumVotes = totalVotes.stream().mapToInt(TotalVote::getTotalVotes).sum();

        List<TotalVoteWithPercentageDTO> result = new ArrayList<>();
        for (TotalVote vote : totalVotes) {
            double percentage = sumVotes > 0 ? (double) vote.getTotalVotes() / sumVotes * 100 : 0;
            result.add(new TotalVoteWithPercentageDTO(vote.getAffiliationName(), vote.getTotalVotes(), percentage));
        }
        return result;
    }
}
