package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "authority_total_votes")
public class AuthorityTotalVote {
    @Id
    private Long id;
    @Column(name = "affiliation_id")
    private Integer affiliationId;

    @Lob
    @Column(name = "authority_id")
    private String authorityId;

    @Column(name = "valid_votes")
    private Integer validVotes;

    public Integer getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(Integer affiliationId) {
        this.affiliationId = affiliationId;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public Integer getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(Integer validVotes) {
        this.validVotes = validVotes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAuthorityIdentifier(String authorityIdentifier) {

    }

    public void setAffiliationIdentifier(String affiliationIdentifier) {
    }
}