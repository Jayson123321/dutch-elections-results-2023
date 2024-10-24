package com.election.backendjava.repositories;

import com.election.backendjava.entities.AuthorityTotalVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultLocalAuthorityRepository extends JpaRepository<AuthorityTotalVote, Long> {
}