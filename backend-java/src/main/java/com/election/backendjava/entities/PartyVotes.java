package com.election.backendjava.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "party_votes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PartyVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporting_unit_id")
    private String reportingUnitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliation_id", nullable = false)
    private Affiliation affiliation;

    @Column(name = "managing_authority_number")
    private String managingAuthorityNumber;

    @Column(name = "reporting_unit_identifier")
    private String reportingUnitIdentifier;

    @Column(name = "valid_votes")
    private int validVotes;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportingUnitId() {
        return reportingUnitId;
    }

    public void setReportingUnitId(String reportingUnitId) {
        this.reportingUnitId = reportingUnitId;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public String getManagingAuthorityNumber() {
        return managingAuthorityNumber;
    }

    public void setManagingAuthorityNumber(String managingAuthorityNumber) {
        this.managingAuthorityNumber = managingAuthorityNumber;
    }

    public String getReportingUnitIdentifier() {
        return reportingUnitIdentifier;
    }

    public void setReportingUnitIdentifier(String reportingUnitIdentifier) {
        this.reportingUnitIdentifier = reportingUnitIdentifier;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }
}