package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_votes")
public class CandidateVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_votecount", nullable = false)
    private int candidateVoteCount;

    @Column(name = "candidate_identifier", nullable = false)
    private String candidateIdentifier;

    @Column(name = "affiliation_id", nullable = false)
    private String affiliationId;

    @Column(name = "managing_authority_identifier", nullable = false)
    private String managingAuthorityIdentifier;

    @Column(name = "managing_authority_number", nullable = false)
    private int managingAuthorityNumber;

    @Column(name = "reporting_unit_id", nullable = false)
    private String reportingUnitId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCandidateVoteCount() {
        return candidateVoteCount;
    }

    public void setCandidateVoteCount(int candidateVoteCount) {
        this.candidateVoteCount = candidateVoteCount;
    }

    public String getCandidateIdentifier() {
        return candidateIdentifier;
    }

    public void setCandidateIdentifier(String candidateIdentifier) {
        this.candidateIdentifier = candidateIdentifier;
    }

    public String getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(String affiliationId) {
        this.affiliationId = affiliationId;
    }

    public String getManagingAuthorityIdentifier() {
        return managingAuthorityIdentifier;
    }

    public void setManagingAuthorityIdentifier(String managingAuthorityIdentifier) {
        this.managingAuthorityIdentifier = managingAuthorityIdentifier;
    }

    public int getManagingAuthorityNumber() {
        return managingAuthorityNumber;
    }

    public void setManagingAuthorityNumber(int managingAuthorityNumber) {
        this.managingAuthorityNumber = managingAuthorityNumber;
    }

    public String getReportingUnitId() {
        return reportingUnitId;
    }

    public void setReportingUnitId(String reportingUnitId) {
        this.reportingUnitId = reportingUnitId;
    }
}