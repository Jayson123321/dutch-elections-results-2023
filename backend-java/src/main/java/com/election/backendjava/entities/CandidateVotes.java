package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_votes")
public class CandidateVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_identifier", nullable = false)
    private String candidateIdentifier;

    @Column(name = "valid_votes", nullable = false)
    private int validVotes;

    // Getters en Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandidateIdentifier() {
        return candidateIdentifier;
    }

    public void setCandidateIdentifier(String candidateIdentifier) {
        this.candidateIdentifier = candidateIdentifier;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }
}
