package com.election.backendjava.service;

import com.election.backendjava.entities.AuthorityTotalVote;
import com.election.backendjava.repositories.ResultLocalAuthorityRepository;
import com.election.backendjava.services.ResultLocalAuthorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ResultLocalAuthorityServiceTest {

    @Mock
    private ResultLocalAuthorityRepository resultLocalAuthorityRepository;

    @InjectMocks
    private ResultLocalAuthorityService resultLocalAuthorityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByAuthorityId() {
        AuthorityTotalVote vote1 = new AuthorityTotalVote();
        vote1.setAuthorityId("auth1");
        vote1.setValidVotes(100);

        AuthorityTotalVote vote2 = new AuthorityTotalVote();
        vote2.setAuthorityId("auth1");
        vote2.setValidVotes(200);

        List<AuthorityTotalVote> expectedVotes = Arrays.asList(vote1, vote2);

        when(resultLocalAuthorityRepository.findByAuthorityId("auth1")).thenReturn(expectedVotes);

        List<AuthorityTotalVote> actualVotes = resultLocalAuthorityService.findByAuthorityId("auth1");
        assertEquals(expectedVotes, actualVotes);
    }
}