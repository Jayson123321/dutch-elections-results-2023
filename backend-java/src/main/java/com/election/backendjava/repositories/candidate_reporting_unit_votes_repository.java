package com.election.backendjava.repositories;

import com.election.backendjava.entities.candidate_reporting_unit_votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface candidate_reporting_unit_votes_repository extends JpaRepository<candidate_reporting_unit_votes, Long> {
    List<candidate_reporting_unit_votes> findByMunicipalityNameIgnoreCase(String municipalityName);
    List<candidate_reporting_unit_votes> findByReportingUnitId(String reportingUnitId);
    List<candidate_reporting_unit_votes> findByCandidate_AffiliationIdAndCandidate_IdAndReportingUnitId(String affiliationId, Long candidateId, String reportingUnitId);
    List<candidate_reporting_unit_votes> findByCandidate_AffiliationIdAndReportingUnitId(String affiliationId, String reportingUnitId);
    List<candidate_reporting_unit_votes> findByCandidate_AffiliationIdAndReportingUnitIdAndManagingAuthorityNumber(String affiliationId, String reportingUnitId, String managingAuthorityNumber);
}