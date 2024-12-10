package com.election.backendjava.controllers;

import com.election.backendjava.entities.candidate_reporting_unit_votes;
import com.election.backendjava.services.CandidateReportingUnitVotesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-reporting-unit-votes")
@CrossOrigin(origins = "http://localhost:8080")
public class CandidateReportingUnitVotesController {

    private final CandidateReportingUnitVotesService service;

    public CandidateReportingUnitVotesController(CandidateReportingUnitVotesService service) {
        this.service = service;
    }

    @GetMapping("/municipality/{municipalityName}")
    public List<candidate_reporting_unit_votes> getByMunicipality(@PathVariable String municipalityName) {
        return service.findByMunicipalityName(municipalityName);
    }

    @GetMapping("/reporting-unit/{reportingUnitId}")
    public List<candidate_reporting_unit_votes> getByReportingUnit(@PathVariable String reportingUnitId) {
        return service.findByReportingUnitId(reportingUnitId);
    }

    @GetMapping("/affiliation/{affiliationId}/Candidate/{candidateId}/reporting-unit/{reportingUnitId}")
    public List<candidate_reporting_unit_votes> getValidVotesByAffiliationIdAndCandidateIdAndReportingUnitId(@PathVariable Long affiliationId, @PathVariable Long candidateId, @PathVariable String reportingUnitId) {
        return service.findValidVotesByAffiliationIdAndCandidateIdAndReportingUnit(affiliationId, candidateId, reportingUnitId);
    }
}