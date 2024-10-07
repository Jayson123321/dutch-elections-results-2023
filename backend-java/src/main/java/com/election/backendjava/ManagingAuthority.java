package com.election.backendjava;

import jakarta.persistence.*;

@Entity
@Table(name = "managing_authority")
public class ManagingAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority_identifier", nullable = false, unique = true)
    private String authorityIdentifier;

    @Column(name = "authority_name", nullable = false)
    private String authorityName;

    public ManagingAuthority() {}

    public ManagingAuthority(String authorityIdentifier, String authorityName) {
        this.authorityIdentifier = authorityIdentifier;
        this.authorityName = authorityName;
    }

    // Getters en Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorityIdentifier() {
        return authorityIdentifier;
    }

    public void setAuthorityIdentifier(String authorityIdentifier) {
        this.authorityIdentifier = authorityIdentifier;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
}
