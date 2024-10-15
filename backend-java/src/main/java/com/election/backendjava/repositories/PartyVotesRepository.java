// backend-java/src/main/java/com/election/backendjava/PartyVotesRepository.java
package com.election.backendjava.repositories;

import com.election.backendjava.PartyVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PartyVotesRepository extends JpaRepository<PartyVotes, Long> {
    List<PartyVotes> findByReportingUnitId(String reportingUnitId);
    List<PartyVotes> findByManagingAuthorityNumberAndReportingUnitId(String authorityIdentifier, String reportingUnitId);
    List<PartyVotes> findByReportingUnitIdAndManagingAuthorityNumber(String reportingUnitId, String managingAuthorityNumber);
}
