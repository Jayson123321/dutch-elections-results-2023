package com.election.backendjava.repositories;

import com.election.backendjava.ReportingUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface ReportingUnitRepository extends JpaRepository<ReportingUnit, Long> {
    List<ReportingUnit> findByManagingAuthority_AuthorityIdentifier(String managingAuthority);
}
