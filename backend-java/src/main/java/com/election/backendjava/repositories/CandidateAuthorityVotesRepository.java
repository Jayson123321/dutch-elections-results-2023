
package com.election.backendjava.repositories;

import com.election.backendjava.entities.CandidateAffiliationVotes;
import com.election.backendjava.entities.CandidateAuthorityVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateAuthorityVotesRepository extends JpaRepository<CandidateAuthorityVotes, Long> {
    List<CandidateAuthorityVotes> findByAffiliationIdAndAuthorityIdentifier(Long affiliationId, String authorityIdentifier);}
