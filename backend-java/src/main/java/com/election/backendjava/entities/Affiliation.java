package com.election.backendjava.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "affiliation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Affiliation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "affiliation_id", unique = true, nullable = false)
    private String affiliationId;

    @Column(name = "registered_name", nullable = false)
    private String registeredName;

    @OneToMany(mappedBy = "affiliation")
    private List<Candidate> candidates;

    public Affiliation() {}

    public Affiliation(String affiliationId, String registeredName) {
        this.affiliationId = affiliationId;
        this.registeredName = registeredName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(String affiliationId) {
        this.affiliationId = affiliationId;
    }

    public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }
}