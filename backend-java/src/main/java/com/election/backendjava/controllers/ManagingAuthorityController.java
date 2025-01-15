package com.election.backendjava.controllers;

import com.election.backendjava.entities.PartyVotes;
import com.election.backendjava.services.PartyVotesService;
import com.election.backendjava.services.ReportingUnitService;
import com.election.backendjava.services.ManagingAuthorityService;
import com.election.backendjava.ReportingUnit;
import com.election.backendjava.entities.ManagingAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managing-authorities")
public class ManagingAuthorityController {

    @Autowired
    private ManagingAuthorityService managingAuthorityService;

    @Autowired
    private PartyVotesService partyVotesService;

    @Autowired
    private ReportingUnitService reportingUnitService;

    @GetMapping("/getAllAuthorities")
    public List<ManagingAuthority> getAllManagingAuthorities() {
        return managingAuthorityService.findAll();
    }

    @GetMapping("/{authorityIdentifier}")
    public List<PartyVotes> getPartyVotesByReportingUnitId(@PathVariable("authorityIdentifier") String authorityIdentifier) {
        return partyVotesService.findByReportingUnitId(authorityIdentifier);
    }

    @GetMapping("/{authorityIdentifier}/reporting-units")
    public List<ReportingUnit> getReportingUnitsByAuthorityIdentifier(@PathVariable("authorityIdentifier") String authorityIdentifier) {
        return reportingUnitService.findByManagingAuthority_AuthorityIdentifier(authorityIdentifier);
    }

    @GetMapping("/{authorityIdentifier}/party-votes/{reportingUnitId}")
    public List<PartyVotes> getPartyVotesByAuthorityAndReportingUnit(
            @PathVariable("authorityIdentifier") String authorityIdentifier,
            @PathVariable("reportingUnitId") String reportingUnitId) {
        return partyVotesService.findByReportingUnitIdAndManagingAuthorityNumber(reportingUnitId, authorityIdentifier);
    }
}