package com.election.backendjava.repositories;

import com.election.backendjava.PartyVotes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyVotesRepository extends JpaRepository<PartyVotes, Long> {
    List<PartyVotes> findByReportingUnitId(String reportingUnitId);
}
