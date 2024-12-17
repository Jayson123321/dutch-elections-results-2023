package com.election.backendjava.repositories;

import com.election.backendjava.entities.BanReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanReasonRepository extends JpaRepository<BanReason, Long> {
}
