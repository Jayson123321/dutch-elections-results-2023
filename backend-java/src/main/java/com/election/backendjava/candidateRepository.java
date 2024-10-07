package com.election.backendjava;

import org.springframework.data.jpa.repository.JpaRepository;

public interface candidateRepository extends JpaRepository<Candidate, Long> {

}
