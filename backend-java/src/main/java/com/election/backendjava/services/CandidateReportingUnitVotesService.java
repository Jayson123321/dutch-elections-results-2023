package com.election.backendjava.services;

import com.election.backendjava.entities.candidate_reporting_unit_votes;
import com.election.backendjava.repositories.candidate_reporting_unit_votes_repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateReportingUnitVotesService {

    private final candidate_reporting_unit_votes_repository repository;

    public CandidateReportingUnitVotesService(candidate_reporting_unit_votes_repository repository) {
        this.repository = repository;
    }

    public List<candidate_reporting_unit_votes> findByReportingUnitId(String reportingUnitId) {
        return repository.findByReportingUnitId(reportingUnitId);
    }

    public List<candidate_reporting_unit_votes> findByMunicipalityName(String municipalityName) {
        return repository.findByMunicipalityNameIgnoreCase(municipalityName);
    }

    public List<candidate_reporting_unit_votes> findValidVotesByAffiliationIdAndCandidateIdAndReportingUnit(Long affiliationId, Long candidateId, String reportingUnitId) {
        return repository.findByCandidate_AffiliationIdAndCandidate_IdAndReportingUnitId(String.valueOf(affiliationId), candidateId, reportingUnitId);
    }

    public List<candidate_reporting_unit_votes> findValidVotesByAffiliationIdAndReportingUnit(Long affiliationId, String reportingUnitId) {
        return repository.findByCandidate_AffiliationIdAndReportingUnitId(String.valueOf(affiliationId), reportingUnitId);
    }

    public List<candidate_reporting_unit_votes> findValidVotesByAffiliationIdAndReportingUnitIdAndManagingAuthorityNumber(Long affiliationId, String reportingUnitId, String managingAuthorityNumber) {
        return repository.findByCandidate_AffiliationIdAndReportingUnitIdAndManagingAuthorityNumber(String.valueOf(affiliationId), reportingUnitId, managingAuthorityNumber);
    }
}