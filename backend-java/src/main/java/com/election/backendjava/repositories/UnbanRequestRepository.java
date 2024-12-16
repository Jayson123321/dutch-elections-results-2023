package com.election.backendjava.repositories;

import com.election.backendjava.entities.UnbanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnbanRequestRepository extends JpaRepository<UnbanRequest, Long> {
}
