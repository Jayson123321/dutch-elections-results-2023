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
    @GetMapping("/affiliation/{affiliationId}/reporting-unit/{reportingUnitId}/")
    public List<candidate_reporting_unit_votes> getValidVotesByAffiliationIdAndReportingUnitId(@PathVariable Long affiliationId, @PathVariable String reportingUnitId) {
        return service.findValidVotesByAffiliationIdAndReportingUnit(affiliationId, reportingUnitId);
    }
    @GetMapping("/reporting-unit/{reportingUnitId}/affiliation/{affiliationId}/managingAuthorityNumber/{managingAuthorityNumber}")
    public List<candidate_reporting_unit_votes> getValidVotesByAffiliationIdAndReportingUnitIdAndManagingAuthorityNumber(@PathVariable String reportingUnitId, @PathVariable Long affiliationId, @PathVariable String managingAuthorityNumber) {
        return service.findValidVotesByAffiliationIdAndReportingUnitIdAndManagingAuthorityNumber(affiliationId, reportingUnitId, managingAuthorityNumber);
    }
    @GetMapping("/candidate/{candidateId}/reporting-unit/{reportingUnitId}")
    public List<candidate_reporting_unit_votes> getByCandidateAndReportingUnit(
            @PathVariable Long candidateId,
            @PathVariable String reportingUnitId) {
        return service.findByCandidateAndReportingUnit(candidateId, reportingUnitId);
    }
    @GetMapping("/reporting-unit/{reportingUnitId}/candidate/{candidateId}")
    public List<candidate_reporting_unit_votes> getByReportingUnitAndCandidate(@PathVariable String reportingUnitId, @PathVariable Long candidateId) {
        return service.findByReportingUnitAndCandidate(reportingUnitId, candidateId);
    }
    @GetMapping("/reporting-unit/{reportingUnitId}/candidate/{candidateId}/affiliation/{affiliationId}")
    public List<candidate_reporting_unit_votes> getByReportingUnitAndCandidate(@PathVariable String reportingUnitId, @PathVariable Long candidateId, @PathVariable String affiliationId) {
        return service.findByReportingUnitAndCandidate(reportingUnitId, affiliationId, candidateId);
    }

}