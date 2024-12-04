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

    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(name = "affiliation_name")
    private String affiliationName;

    @Column(name = "valid_votes")
    private int validVotes;

    @Column(name = "affiliation_id")
    private String affiliationId;

    // Getters and Setters

    public Long getId() {
        return id;
    }

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

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
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

    public String getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(String affiliationId) {
        this.affiliationId = affiliationId;
    }

    @Override
    public String toString() {
        return "candidate_reporting_unit_votes{" +
                "municipalityName='" + municipalityName + '\'' +
                ", reportingUnitId='" + reportingUnitId + '\'' +
                ", reportingUnitName='" + reportingUnitName + '\'' +
                ", candidateId=" + candidateId +
                ", affiliationName='" + affiliationName + '\'' +
                ", validVotes=" + validVotes +
                ", affiliationId=" + affiliationId +
                '}';
    }
}