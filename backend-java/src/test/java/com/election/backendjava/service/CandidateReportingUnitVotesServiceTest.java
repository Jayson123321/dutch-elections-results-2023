package com.election.backendjava.service;

import com.election.backendjava.entities.candidate_reporting_unit_votes;
import com.election.backendjava.repositories.candidate_reporting_unit_votes_repository;
import com.election.backendjava.services.CandidateReportingUnitVotesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CandidateReportingUnitVotesServiceTest {

    @Mock
    private candidate_reporting_unit_votes_repository repository;

    @InjectMocks
    private CandidateReportingUnitVotesService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByReportingUnitId() {
        String reportingUnitId = "unit1";
        candidate_reporting_unit_votes vote1 = new candidate_reporting_unit_votes();
        candidate_reporting_unit_votes vote2 = new candidate_reporting_unit_votes();
        List<candidate_reporting_unit_votes> expectedVotes = Arrays.asList(vote1, vote2);

        when(repository.findByReportingUnitId(reportingUnitId)).thenReturn(expectedVotes);

        List<candidate_reporting_unit_votes> actualVotes = service.findByReportingUnitId(reportingUnitId);

        assertEquals(expectedVotes, actualVotes);
    }
}