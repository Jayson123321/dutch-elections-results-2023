package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "municipalities")
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "municipality_id", unique = true, nullable = false)
    private String municipalityId;

    @Column(name = "name", nullable = false)
    private String name;

    public Municipality() {}

    public Municipality(String municipalityId, String name) {
        this.municipalityId = municipalityId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(String municipalityId) {
        this.municipalityId = municipalityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
