package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "authority_total_votes")
public class AuthorityTotalVote {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "affiliation_id", referencedColumnName = "affiliation_id")
    private Affiliation affiliation;

    @Lob
    @Column(name = "authority_id")
    private String authorityId;

    @Column(name = "valid_votes")
    private Integer validVotes;

    public AuthorityTotalVote(String authorityId, int i) {
    }

    public AuthorityTotalVote() {

    }

    public AuthorityTotalVote(String auth123, String candidate2, int i) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
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

    }

    public void setAuthorityIdentifier(String authorityIdentifier) {
    }

    public void setAffiliationIdentifier(String affiliationIdentifier) {
    }
}