package com.election.backendjava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyVotesRepository extends JpaRepository<PartyVotes, Long> {
}
