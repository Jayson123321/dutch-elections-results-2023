package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managing-authorities")
public class ManagingAuthorityController {

    @Autowired
    private ManagingAuthorityRepository managingAuthorityRepository;

    @Autowired
    private PartyVotesRepository partyVotesRepository;

    @Autowired
    private ReportingUnitRepository reportingUnitRepository;

    @GetMapping
    public List<ManagingAuthority> getAllManagingAuthorities() {
        return managingAuthorityRepository.findAll();
    }


    @GetMapping("/{authorityIdentifier}/party-votes")
    public List<PartyVotes> getPartyVotesByAuthorityIdentifier(@PathVariable("authorityIdentifier") String authorityIdentifier) {
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

