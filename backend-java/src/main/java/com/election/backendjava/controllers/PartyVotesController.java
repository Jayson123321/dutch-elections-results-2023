package com.election.backendjava.controllers;

import com.election.backendjava.entities.PartyVotes;
import com.election.backendjava.repositories.PartyVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/party-votes")
public class PartyVotesController {

    @Autowired
    private PartyVotesRepository partyVotesRepository;

    @GetMapping("/reporting-unit/{reportingUnitId}")
    public List<PartyVotes> getPartyVotesByReportingUnit(@PathVariable String reportingUnitId) {
        return partyVotesRepository.findByReportingUnitId(reportingUnitId);
    }


}