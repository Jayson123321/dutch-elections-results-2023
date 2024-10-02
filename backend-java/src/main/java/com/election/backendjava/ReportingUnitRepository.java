package com.election.backendjava;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportingUnitRepository extends JpaRepository<ReportingUnit, Long> {
}
