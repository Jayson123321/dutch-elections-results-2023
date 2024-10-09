package com.election.backendjava;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface candidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByAffiliationId(Long affiliationId);

}
