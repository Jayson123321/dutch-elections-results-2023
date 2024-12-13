package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_reporting_unit_votes")
public class candidate_reporting_unit_votes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "municipality_name")
    private String municipalityName;

    @Column(name = "reporting_unit_id")
    private String reportingUnitId;

    @Column(name = "reporting_unit_name")
    private String reportingUnitName;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_identifier", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "affiliation_id", referencedColumnName = "affiliation_id", nullable = false, insertable = false, updatable = false)
    })
    private Candidate candidate;

    @Column(name = "affiliation_name")
    private String affiliationName;

    @Column(name = "valid_votes")
    private int validVotes;

    @Column(name = "managing_authority_number")
    private String managingAuthorityNumber;

    // Getters and Setters

    public void setId(Long id) {
        this.id = id;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public String getReportingUnitId() {
        return reportingUnitId;
    }

    public void setReportingUnitId(String reportingUnitId) {
        this.reportingUnitId = reportingUnitId;
    }

    public String getReportingUnitName() {
        return reportingUnitName;
    }

    public void setReportingUnitName(String reportingUnitName) {
        this.reportingUnitName = reportingUnitName;
    }

    public String getAffiliationName() {
        return affiliationName;
    }

    public void setAffiliationName(String affiliationName) {
        this.affiliationName = affiliationName;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }

    public String getManagingAuthorityNumber() {
        return managingAuthorityNumber;
    }

    public void setManagingAuthorityNumber(String managingAuthorityNumber) {
        this.managingAuthorityNumber = managingAuthorityNumber;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getCandidateName() {
        return candidate.getCandidateName();
    }

    public String getAffiliationId() {
        return candidate.getAffiliationId();
    }
}