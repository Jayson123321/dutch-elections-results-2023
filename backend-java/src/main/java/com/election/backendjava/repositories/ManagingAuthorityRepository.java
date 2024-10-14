package com.election.backendjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagingAuthorityRepository extends JpaRepository<ManagingAuthority, Long> {
    Optional<ManagingAuthority> findByAuthorityIdentifier(String authorityIdentifier);
    Optional<ManagingAuthority> findByAuthorityName(String authorityName);
}
