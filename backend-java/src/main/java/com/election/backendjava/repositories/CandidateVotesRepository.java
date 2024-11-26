package com.election.backendjava.repositories;

import com.election.backendjava.entities.CandidateVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateVotesRepository extends JpaRepository<CandidateVotes, Long> {
    Optional<CandidateVotes> findById(Long id);
}

