package com.election.backendjava;

import jakarta.persistence.*;

@Entity
@Table(name = "party_votes")
public class PartyVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valid_votes")
    private int validVotes;

    @Column(name = "reporting_unit_identifier")
    private String reportingUnitIdentifier;

    @Column(name = "reporting_unit_id")
    private String reportingUnitId;

    @Column(name = "affiliation_id")
    private int affiliationId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }

    public String getReportingUnitIdentifier() {
        return reportingUnitIdentifier;
    }

    public void setReportingUnitIdentifier(String reportingUnitIdentifier) {
        this.reportingUnitIdentifier = reportingUnitIdentifier;
    }

    public String getReportingUnitId() {
        return reportingUnitId;
    }

    public void setReportingUnitId(String reportingUnitId) {
        this.reportingUnitId = reportingUnitId;
    }

    public int getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(int affiliationId) {
        this.affiliationId = affiliationId;
    }
}
