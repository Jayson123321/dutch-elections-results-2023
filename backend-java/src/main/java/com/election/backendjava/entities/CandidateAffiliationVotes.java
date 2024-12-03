package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_affiliation_votes")
public class CandidateAffiliationVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_identifier_id", nullable = false)
    private Long candidateIdentifierId;

    @Column(name = "affiliation_id", nullable = false)
    private long affiliationId;

    @Column(name = "authority_identifier", nullable = false)
    private Long authorityIdentifier;

    @Column(name = "authority_name", nullable = false)
    private String authorityName;

    @Column(name = "valid_votes", nullable = false)
    private int validVotesAffiliation;

    @Column (name = "registered_name", nullable = false)
    private String registeredName;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCandidateIdentifierId() {
        return candidateIdentifierId;
    }

    public void setCandidateIdentifierId(Long candidateIdentifierId) {
        this.candidateIdentifierId = candidateIdentifierId;
    }

    public long getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(long affiliationId) {
        this.affiliationId = affiliationId;
    }

    public Long getAuthorityIdentifier() {
        return authorityIdentifier;
    }

    public void setAuthorityIdentifier(Long authorityIdentifier) {
        this.authorityIdentifier = authorityIdentifier;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public int getValidVotes() {
        return validVotesAffiliation;
    }

    public void setValidVotes(int validVotes) {
        this.validVotesAffiliation = validVotes;
    }

    public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }
}
