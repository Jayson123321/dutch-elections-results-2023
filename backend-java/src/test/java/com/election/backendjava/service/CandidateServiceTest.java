package com.election.backendjava.service;

import com.election.backendjava.entities.Candidate;
import com.election.backendjava.repositories.CandidateRepository;
import com.election.backendjava.services.CandidateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CandidateServiceTest {

    // Mock het CandidateRepository dat betekent dat we de echte repository niet gebruiken
    @Mock
    private CandidateRepository candidateRepository;

    // InjectMocks betekent dat we de CandidateService testen en dat de CandidateRepository mock wordt geïnjecteerd
    @InjectMocks
    private CandidateService candidateService;

    // Voer deze methode uit voordat elke test wordt uitgevoerd
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test de getAllCandidates methode van CandidateService
    @Test
    public void testGetAllCandidates() {
        // Maak een lijst van mock kandidaten
        List<Candidate> candidates = Arrays.asList(new Candidate(), new Candidate());

        // Stel het gedrag van het mock repository in
        when(candidateRepository.findAll()).thenReturn(candidates);

        // Roep de methode aan en controleer het resultaat
        List<Candidate> result = candidateService.getAllCandidates();
        assertEquals(2, result.size());
    }

    // Test de getCandidatesByAffiliationId methode van CandidateService
    @Test
    public void testGetCandidatesByAffiliationId() {
        // Maak een lijst van mock kandidaten
        List<Candidate> candidates = Arrays.asList(new Candidate(), new Candidate());

        // Stel het gedrag van het mock repository in
        when(candidateRepository.findByAffiliationId("1")).thenReturn(candidates);

        // Roep de methode aan en controleer het resultaat
        List<Candidate> result = candidateService.getCandidatesByAffiliationId("1");
        assertEquals(2, result.size());
    }
}