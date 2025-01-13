package com.election.backendjava.services;

import com.election.backendjava.entities.PartyVotes;
import com.election.backendjava.repositories.PartyVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyVotesService {

    @Autowired
    private PartyVotesRepository partyVotesRepository;

    public List<PartyVotes> findByReportingUnitId(String authorityIdentifier) {
        return partyVotesRepository.findByReportingUnitId(authorityIdentifier);
    }
    public List<PartyVotes> findByReportingUnitIdAndManagingAuthorityNumber(String reportingUnitId, String authorityIdentifier) {
        return partyVotesRepository.findByReportingUnitIdAndManagingAuthorityNumber(reportingUnitId, authorityIdentifier);
    }
}
