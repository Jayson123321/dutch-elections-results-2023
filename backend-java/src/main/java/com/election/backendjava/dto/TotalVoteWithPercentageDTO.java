package com.election.backendjava.dto;

public class TotalVoteWithPercentageDTO {
    private String affiliationName;
    private Integer totalVotes;
    private Double percentage;

    public TotalVoteWithPercentageDTO(String affiliationName, Integer totalVotes, Double percentage) {
        this.affiliationName = affiliationName;
        this.totalVotes = totalVotes;
        this.percentage = percentage;
    }

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

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
    // test
}
