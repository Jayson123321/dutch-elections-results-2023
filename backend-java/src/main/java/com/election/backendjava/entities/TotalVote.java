package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "total_votes")
public class TotalVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String affiliationName;

    private Integer totalVotes;

    private Integer affiliationId;


    //getters and setters

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