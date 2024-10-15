package com.election.backendjava;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TotalVoteRepository extends JpaRepository<TotalVote,Long> {

}