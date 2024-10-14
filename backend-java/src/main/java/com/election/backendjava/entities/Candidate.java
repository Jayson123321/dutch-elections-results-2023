package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_identifier", nullable = false)
    private String candidateIdentifier;

    @Column(name = "candidate_name", nullable = false)
    private String candidateName;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "qualifying_address", nullable = false)
    private String qualifyingAddress;

    @Column(name = "affiliation_id", nullable = false)
    private String affiliationId;

    public Candidate() {}

    public Candidate(String candidateIdentifier, String candidateName, String gender, String qualifyingAddress, String affiliationId) {
        this.candidateIdentifier = candidateIdentifier;
        this.candidateName = candidateName;
        this.gender = gender;
        this.qualifyingAddress = qualifyingAddress;
        this.affiliationId = affiliationId;
    }

    // Getters and Setters
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

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getQualifyingAddress() {
        return qualifyingAddress;
    }

    public void setQualifyingAddress(String qualifyingAddress) {
        this.qualifyingAddress = qualifyingAddress;
    }

    public String getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(String affiliationId) {
        this.affiliationId = affiliationId;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", candidateIdentifier='" + candidateIdentifier + '\'' +
                ", candidateName='" + candidateName + '\'' +
                ", gender='" + gender + '\'' +
                ", qualifyingAddress='" + qualifyingAddress + '\'' +
                ", affiliationId=" + affiliationId +
                '}';
    }
}
