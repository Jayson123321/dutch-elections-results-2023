package com.election.backendjava.service;

import com.election.backendjava.dto.TotalVoteWithPercentageDTO;
import com.election.backendjava.entities.TotalVote;
import com.election.backendjava.repositories.TotalVoteRepository;
import com.election.backendjava.services.TotalVoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TotalVoteServiceTest {

    @Mock
    private TotalVoteRepository totalVoteRepository;

    @InjectMocks
    private TotalVoteService totalVoteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVotesSortedByAffiliationName() {
        // Arrange
        TotalVote vote1 = new TotalVote("Party B", 200);
        TotalVote vote2 = new TotalVote("Party A", 300);
        when(totalVoteRepository.findAll()).thenReturn(Arrays.asList(vote1, vote2));

        // Act
        List<TotalVote> result = totalVoteService.getAllVotesSortedByAffiliationName();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Party A", result.get(0).getAffiliationName());
        assertEquals("Party B", result.get(1).getAffiliationName());
    }

    @Test
    void testGetAllVotesSortedByTotalVotes() {
        // Arrange
        TotalVote vote1 = new TotalVote("Party A", 200);
        TotalVote vote2 = new TotalVote("Party B", 300);
        when(totalVoteRepository.findAll()).thenReturn(Arrays.asList(vote1, vote2));

        // Act
        List<TotalVote> result = totalVoteService.getAllVotesSortedByTotalVotes();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Party B", result.get(0).getAffiliationName());
        assertEquals("Party A", result.get(1).getAffiliationName());
    }

    @Test
    void testGetAllTotalVotesWithPercentage() {
        // Arrange
        TotalVote vote1 = new TotalVote("Party A", 200);
        TotalVote vote2 = new TotalVote("Party B", 300);
        when(totalVoteRepository.findAll()).thenReturn(Arrays.asList(vote1, vote2));

        // Act
        List<TotalVoteWithPercentageDTO> result = totalVoteService.getAllTotalVotesWithPercentage();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Party A", result.get(0).getAffiliationName());
        assertEquals(40.0, result.get(0).getPercentage());
        assertEquals("Party B", result.get(1).getAffiliationName());
        assertEquals(60.0, result.get(1).getPercentage());
    }
}
