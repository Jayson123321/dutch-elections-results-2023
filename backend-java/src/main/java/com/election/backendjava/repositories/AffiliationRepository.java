package com.election.backendjava.repositories;

import com.election.backendjava.entities.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
}