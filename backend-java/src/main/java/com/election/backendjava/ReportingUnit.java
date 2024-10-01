package com.election.backendjava;

import jakarta.persistence.*;

@Entity
@Table(name = "reporting_unit")
public class ReportingUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)  // Verwijst naar ManagingAuthority
    @JoinColumn(name = "managing_authority_id", nullable = false)
    private ManagingAuthority managingAuthority;

    public ReportingUnit() {}

    public ReportingUnit(String name, String postalCode, ManagingAuthority managingAuthority) {
        this.name = name;
        this.managingAuthority = managingAuthority;
    }

    // Getters en Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ManagingAuthority getManagingAuthority() {
        return managingAuthority;
    }

    public void setManagingAuthority(ManagingAuthority managingAuthority) {
        this.managingAuthority = managingAuthority;
    }
}
