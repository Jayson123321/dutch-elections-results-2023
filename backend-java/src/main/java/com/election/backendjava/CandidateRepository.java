package com.election.backendjava;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    // Haal kandidaten op basis van affiliationId
    List<Candidate> findByAffiliationId(String affiliationId);
}
