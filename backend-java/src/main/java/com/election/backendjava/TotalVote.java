package com.election.backendjava;

import jakarta.persistence.*;

@Entity
@Table(name = "total_votes")
public class TotalVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "affiliation_name")
    private String affiliationName;

    @Column(name = "total_votes")
    private Integer totalVotes;

    @Column(name = "affiliation_id")
    private Integer affiliationId;

    public String getAffiliationName() {
        return affiliationName;
    }

    public void setAffiliationName(String affiliationName) {
        this.affiliationName = affiliationName;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Integer getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(Integer affiliationId) {
        this.affiliationId = affiliationId;
    }

}