package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
public class ResultLocalAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authorityIdentifier;
    private String affiliationIdentifier;
    private int validVotes;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorityIdentifier() {
        return authorityIdentifier;
    }

    public void setAuthorityIdentifier(String authorityIdentifier) {
        this.authorityIdentifier = authorityIdentifier;
    }

    public String getAffiliationIdentifier() {
        return affiliationIdentifier;
    }

    public void setAffiliationIdentifier(String affiliationIdentifier) {
        this.affiliationIdentifier = affiliationIdentifier;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }
}