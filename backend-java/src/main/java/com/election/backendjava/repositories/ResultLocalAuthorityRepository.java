package com.election.backendjava.repositories;

import com.election.backendjava.entities.AuthorityTotalVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultLocalAuthorityRepository extends JpaRepository<AuthorityTotalVote, Long> {
    List<AuthorityTotalVote> findByAuthorityId(String authorityId);
    // Get all the votes for a specific authority sorted by votes
    List<AuthorityTotalVote> findByAuthorityIdOrderByValidVotesDesc(String authorityId);
}