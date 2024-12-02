package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_authority_votes")
public class CandidateAuthorityVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "authority_identifier", nullable = false)
    private ManagingAuthority authorityIdentifier;

    @ManyToOne
    @JoinColumn(name = "affiliation_identifier", nullable = false)
    private Affiliation affiliationIdentifier;

    @ManyToOne
    @JoinColumn(name = "candidate_identifier", nullable = false)
    private Candidate candidateIdentifier;

    @Column(name = "valid_votes", nullable = false)
    private int validVotes;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ManagingAuthority getAuthorityIdentifier() {
        return authorityIdentifier;
    }

    public void setAuthorityIdentifier(ManagingAuthority authorityIdentifier) {
        this.authorityIdentifier = authorityIdentifier;
    }

    public Affiliation getAffiliationIdentifier() {
        return affiliationIdentifier;
    }

    public void setAffiliationIdentifier(Affiliation affiliationIdentifier) {
        this.affiliationIdentifier = affiliationIdentifier;
    }

    public Candidate getCandidateIdentifier() {
        return candidateIdentifier;
    }

    public void setCandidateIdentifier(Candidate candidateIdentifier) {
        this.candidateIdentifier = candidateIdentifier;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }
}