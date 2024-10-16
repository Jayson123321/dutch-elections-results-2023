package com.election.backendjava.repositories;

import com.election.backendjava.entities.CandidateVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateVotesRepository extends JpaRepository<CandidateVotes, Long> {
}