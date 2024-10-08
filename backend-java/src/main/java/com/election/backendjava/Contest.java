package com.election.backendjava;


import jakarta.persistence.*;

@Entity
@Table(name = "contest")
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contest_identifier", nullable = false)
    private String contestIdentifier;
    @Column(name = "contest_name", nullable = false)
    private String contestName;

    public Contest() {}

    public Contest(String contestIdentifier, String contestName) {
        this.contestIdentifier = contestIdentifier;
        this.contestName = contestName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContestIdentifier() {
        return contestIdentifier;
    }
    public void setContestIdentifier(String contestIdentifier) {
        this.contestIdentifier = contestIdentifier;
    }
    public String getContestName() {
        return contestName;
    }
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }


}
