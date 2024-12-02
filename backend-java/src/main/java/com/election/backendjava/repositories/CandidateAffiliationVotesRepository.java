package com.election.backendjava.repositories;

import com.election.backendjava.entities.CandidateAffiliationVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateAffiliationVotesRepository extends JpaRepository<CandidateAffiliationVotes, Long> {
}
