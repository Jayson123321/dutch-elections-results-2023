package com.election.backendjava.controllers;


import com.election.backendjava.dto.TotalVoteWithPercentageDTO;
import com.election.backendjava.entities.TotalVote;
import com.election.backendjava.services.TotalVoteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TotalVoteControllerTest {

    @Test
    void testGetAllVotesSortedByAffiliationName() {
        // Arrange
        TotalVoteService mockService = mock(TotalVoteService.class);
        TotalVoteController controller = new TotalVoteController(mockService);

        List<TotalVote> mockVotes = Arrays.asList(
                new TotalVote("Party A", 100),
                new TotalVote("Party B", 200)
        );

        when(mockService.getAllVotesSortedByAffiliationName()).thenReturn(mockVotes);

        // Act
        List<TotalVote> result = controller.getAllVotesSortedByAffiliationName();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mockService, times(1)).getAllVotesSortedByAffiliationName();
    }

    @Test
    void testGetAllVotesSortedByTotalVotes() {
        // Arrange
        TotalVoteService mockService = mock(TotalVoteService.class);
        TotalVoteController controller = new TotalVoteController(mockService);

        List<TotalVote> mockVotes = Arrays.asList(
                new TotalVote("Party B", 200),
                new TotalVote("Party A", 100)
        );

        when(mockService.getAllVotesSortedByTotalVotes()).thenReturn(mockVotes);

        // Act
        List<TotalVote> result = controller.getAllVotesSortedByTotalVotes();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Party B", result.get(0).getAffiliationName());
        verify(mockService, times(1)).getAllVotesSortedByTotalVotes();
    }

    @Test
    void testGetAllTotalVotes() {
        // Arrange
        TotalVoteService mockService = mock(TotalVoteService.class);
        TotalVoteController controller = new TotalVoteController(mockService);

        List<TotalVoteWithPercentageDTO> mockDtoList = Arrays.asList(
                new TotalVoteWithPercentageDTO("Party A", 100, 50.0),
                new TotalVoteWithPercentageDTO("Party B", 100, 50.0)
        );

        when(mockService.getAllTotalVotesWithPercentage()).thenReturn(mockDtoList);

        // Act
        List<TotalVoteWithPercentageDTO> result = controller.getAllTotalVotes();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(50.0, result.get(0).getPercentage());
        verify(mockService, times(1)).getAllTotalVotesWithPercentage();
    }
}

