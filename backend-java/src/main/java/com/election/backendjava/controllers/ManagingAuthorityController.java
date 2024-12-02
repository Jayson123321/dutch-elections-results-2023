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
// Allles uit de ManagingAuthority tabel halen.
    @GetMapping("/getAllAuthorities")
    public List<ManagingAuthority> getAllManagingAuthorities() {
        return managingAuthorityRepository.findAll();
    }
    // Selecteert uit partyVotes tabel alles waar de authorityIdentifier gelijk is aan de waarde die in de URL staat.
    @GetMapping("/{authorityIdentifier}")
    public List<PartyVotes> getPartyVotesByReportingUnitId(@PathVariable("authorityIdentifier") String authorityIdentifier) {
        return partyVotesRepository.findByReportingUnitId(authorityIdentifier);
    }
//  Selecteert uit ReportingUnit tabel alles waar de kolom: managing_authority_identifier gelijk is aan de waarde die in de URL staat.
    @GetMapping("/{authorityIdentifier}/reporting-units")
    public List<ReportingUnit> getReportingUnitsByAuthorityIdentifier(@PathVariable("authorityIdentifier") String authorityIdentifier) {
        return reportingUnitRepository.findByManagingAuthority_AuthorityIdentifier(authorityIdentifier);
    }
// Test Postman http://localhost:8080/api/managing-authorities/SB1/party-votes/1680 = uitslag van stembureau SB1 Id 1680(AA Hunze).
    @GetMapping("/{authorityIdentifier}/party-votes/{reportingUnitId}")
    public List<PartyVotes> getPartyVotesByAuthorityAndReportingUnit(
            @PathVariable("authorityIdentifier") String authorityIdentifier,
            @PathVariable("reportingUnitId") String reportingUnitId) {
        return partyVotesRepository.findByReportingUnitIdAndManagingAuthorityNumber(reportingUnitId, authorityIdentifier);
    }


}


