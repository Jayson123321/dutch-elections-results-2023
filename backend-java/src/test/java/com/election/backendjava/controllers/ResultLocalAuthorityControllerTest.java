package com.election.backendjava.controllers;

import com.election.backendjava.entities.AuthorityTotalVote;
import com.election.backendjava.entities.CandidateAuthorityVotes;
import com.election.backendjava.repositories.CandidateAuthorityVotesRepository;
import com.election.backendjava.repositories.ResultLocalAuthorityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ResultLocalAuthorityControllerTest {

    @Mock
    private ResultLocalAuthorityRepository resultLocalAuthorityRepository;

    @Mock
    private CandidateAuthorityVotesRepository candidateAuthorityVotesRepository;

    @InjectMocks
    private ResultLocalAuthorityController resultLocalAuthorityController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for getAuthorityTotalVotesSortedByVotes
    @Test
    public void testGetVotesByAffiliationAndAuthoritySortedByVotes() {
        // Nepversie van de database en zeggen dat deze twee stemmen moet teruggeven
        Long affiliationId = 1L;
        String authorityId = "1";
        // Lijst van verwachte stemmen met specifieke gegevens
        CandidateAuthorityVotes vote1 = new CandidateAuthorityVotes();
        vote1.setId(1L);
        vote1.setAuthorityIdentifier("1");
        vote1.setValidVotes(100);

        CandidateAuthorityVotes vote2 = new CandidateAuthorityVotes();
        vote2.setId(2L);
        vote2.setAuthorityIdentifier("1");
        vote2.setValidVotes(200);

        List<CandidateAuthorityVotes> expectedVotes = Arrays.asList(vote1, vote2);

        // Nepversie van de database deze lijst van verwachte stemmen moet teruggeven als we zoeken op affiliationId en authorityId, gesorteerd op geldige stemmen in aflopende volgorde
        when(candidateAuthorityVotesRepository.findByAffiliationIdAndAuthorityIdentifierOrderByValidVotesDesc(affiliationId, authorityId)).thenReturn(expectedVotes);

        // Roepen de methode van de controller aan met de gegeven affiliationId en authorityId
        List<CandidateAuthorityVotes> actualVotes = resultLocalAuthorityController.getVotesByAffiliationAndAuthoritySortedByVotes(affiliationId, authorityId);

        // Controleren of de stemmen die we terugkrijgen van de controller hetzelfde zijn als de stemmen die we verwachten
        assertEquals(expectedVotes, actualVotes);
    }
}