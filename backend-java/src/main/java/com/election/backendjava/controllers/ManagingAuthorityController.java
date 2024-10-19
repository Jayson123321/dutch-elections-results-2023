package com.election.backendjava.controllers;

import com.election.backendjava.entities.PartyVotes;
import com.election.backendjava.ReportingUnit;
import com.election.backendjava.entities.ManagingAuthority;
import com.election.backendjava.repositories.ManagingAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managing-authorities")
public class ManagingAuthorityController {

    @Autowired
    private ManagingAuthorityRepository managingAuthorityRepository;

    @Autowired
    private com.election.backendjava.repositories.PartyVotesRepository partyVotesRepository;

    @Autowired
    private com.election.backendjava.repositories.ReportingUnitRepository reportingUnitRepository;

    @GetMapping("/getAllAuthorities")
    public List<ManagingAuthority> getAllManagingAuthorities() {
        return managingAuthorityRepository.findAll();
    }
    // PathVariable zorgt ervoor dat de waarde uit URL wordt gehaald en in Variabele parameter wordt gestopt.
    @GetMapping("/{authorityIdentifier}")
    public List<PartyVotes> getPartyVotesByReportingUnitId(@PathVariable("authorityIdentifier") String authorityIdentifier) {
        return partyVotesRepository.findByReportingUnitId(authorityIdentifier);
    }

    @GetMapping("/{authorityIdentifier}/reporting-units")
    public List<ReportingUnit> getReportingUnitsByAuthorityIdentifier(@PathVariable("authorityIdentifier") String authorityIdentifier) {
        return reportingUnitRepository.findByManagingAuthority_AuthorityIdentifier(authorityIdentifier);
    }

    @GetMapping("/{authorityIdentifier}/party-votes/{reportingUnitId}")
    public List<PartyVotes> getPartyVotesByAuthorityAndReportingUnit(
            @PathVariable("authorityIdentifier") String authorityIdentifier,
            @PathVariable("reportingUnitId") String reportingUnitId) {
        return partyVotesRepository.findByReportingUnitIdAndManagingAuthorityNumber(reportingUnitId, authorityIdentifier);
    }



}


