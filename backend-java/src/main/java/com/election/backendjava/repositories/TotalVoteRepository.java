package com.election.backendjava.repositories;

import com.election.backendjava.entities.TotalVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TotalVoteRepository extends JpaRepository<TotalVote,Long> {

}