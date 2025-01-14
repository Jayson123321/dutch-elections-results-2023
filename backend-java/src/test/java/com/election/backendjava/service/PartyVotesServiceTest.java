package com.election.backendjava.service;

import com.election.backendjava.entities.PartyVotes;
import com.election.backendjava.repositories.PartyVotesRepository;
import com.election.backendjava.services.PartyVotesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PartyVotesServiceTest {

    @Mock
    private PartyVotesRepository partyVotesRepository;

    @InjectMocks
    private PartyVotesService partyVotesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByReportingUnitId() {
        String authorityIdentifier = "testAuthority";
        PartyVotes partyVotes = new PartyVotes();
        List<PartyVotes> expectedVotes = Collections.singletonList(partyVotes);

        when(partyVotesRepository.findByReportingUnitId(authorityIdentifier)).thenReturn(expectedVotes);

        List<PartyVotes> actualVotes = partyVotesService.findByReportingUnitId(authorityIdentifier);

        assertEquals(expectedVotes, actualVotes);
    }

    @Test
    public void testFindByReportingUnitIdAndManagingAuthorityNumber() {
        String reportingUnitId = "testUnit";
        String authorityIdentifier = "testAuthority";
        PartyVotes partyVotes = new PartyVotes();
        List<PartyVotes> expectedVotes = Collections.singletonList(partyVotes);

        when(partyVotesRepository.findByReportingUnitIdAndManagingAuthorityNumber(reportingUnitId, authorityIdentifier)).thenReturn(expectedVotes);

        List<PartyVotes> actualVotes = partyVotesService.findByReportingUnitIdAndManagingAuthorityNumber(reportingUnitId, authorityIdentifier);

        assertEquals(expectedVotes, actualVotes);
    }
}