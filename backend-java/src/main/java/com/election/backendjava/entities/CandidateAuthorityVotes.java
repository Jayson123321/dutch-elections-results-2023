package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_authority_votes")
public class CandidateAuthorityVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority_identifier", nullable = false)
    private String authorityIdentifier;

    @ManyToOne
    @JoinColumn(name = "authority_identifier", referencedColumnName = "authority_identifier", nullable = false, insertable = false, updatable = false)
    private ManagingAuthority managingAuthority;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "candidate_identifier", referencedColumnName = "candidate_identifier", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "affiliation_identifier", referencedColumnName = "affiliation_id", nullable = false, insertable = false, updatable = false)
    })
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "affiliation_identifier", nullable = false)
    private Affiliation affiliation;

    @Column(name = "valid_votes", nullable = false)
    private int validVotes;

    // Getters and Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthorityIdentifier(String authorityIdentifier) {
        this.authorityIdentifier = authorityIdentifier;
    }

    public void setManagingAuthority(ManagingAuthority managingAuthority) {
        this.managingAuthority = managingAuthority;
    }

    public String getAuthorityName() {
        return managingAuthority.getAuthorityName();
    }

    public String getRegisteredName() {
        return affiliation.getRegisteredName();
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }

    public String getCandidateName() {
        return candidate.getCandidateName();
    }

    public void setCandidateIdentifier(String candidateIdentifier) {
        this.candidate.setId(Long.valueOf(candidateIdentifier));
    }

    public void setAffiliationIdentifier(String currentAffiliationId) {
        this.affiliation.setId(Long.valueOf(currentAffiliationId));
    }
}