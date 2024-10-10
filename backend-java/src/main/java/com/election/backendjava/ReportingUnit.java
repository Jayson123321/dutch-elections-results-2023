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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managing_authority_identifier", referencedColumnName = "authority_identifier", nullable = false)
    private ManagingAuthority managingAuthority;

    @Column(name = "managing_authority_number", nullable = false)
    private String managingAuthorityNumber;

    public ReportingUnit() {}

    public ReportingUnit(String name, ManagingAuthority managingAuthority, String managingAuthorityNumber) {
        this.name = name;
        this.managingAuthority = managingAuthority;
        this.managingAuthorityNumber = managingAuthorityNumber;
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

    public String getManagingAuthorityNumber() {
        return managingAuthorityNumber;
    }

    public void setManagingAuthorityNumber(String managingAuthorityNumber) {
        this.managingAuthorityNumber = managingAuthorityNumber;
    }
}
