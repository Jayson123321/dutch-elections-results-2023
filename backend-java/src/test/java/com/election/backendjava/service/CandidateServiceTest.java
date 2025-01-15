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

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateService candidateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCandidates() {
        List<Candidate> candidates = Arrays.asList(new Candidate(), new Candidate());

        when(candidateRepository.findAll()).thenReturn(candidates);

        List<Candidate> result = candidateService.getAllCandidates();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetCandidatesByAffiliationId() {
        List<Candidate> candidates = Arrays.asList(new Candidate(), new Candidate());

        when(candidateRepository.findByAffiliationId("1")).thenReturn(candidates);

        List<Candidate> result = candidateService.getCandidatesByAffiliationId("1");
        assertEquals(2, result.size());
    }
}