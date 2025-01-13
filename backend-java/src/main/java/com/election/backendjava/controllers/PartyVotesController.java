package com.election.backendjava.controllers;

import com.election.backendjava.entities.PartyVotes;
import com.election.backendjava.services.PartyVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/party-votes")
public class PartyVotesController {

    @Autowired
    private PartyVotesService partyVotesService;

    @GetMapping("/reporting-unit/{reportingUnitId}")
    public List<PartyVotes> getPartyVotesByReportingUnit(@PathVariable String reportingUnitId) {
        return partyVotesService.findByReportingUnitId(reportingUnitId);
    }

    @GetMapping("/reporting-unit/{reportingUnitId}/sortedByVotes")
    public List<PartyVotes> getPartyVotesByReportingUnitSortedByVotes(@PathVariable String reportingUnitId) {
        return partyVotesService.findByReportingUnitIdOrderByValidVotesDesc(reportingUnitId);
    }
    // sort by name
    @GetMapping("/reporting-unit/{reportingUnitId}/sortedByName")
    public List<PartyVotes> getPartyVotesByReportingUnitSortedByName(@PathVariable String reportingUnitId) {
        return partyVotesService.findByReportingUnitIdOrderByAffiliation(reportingUnitId);
    }

}