// src/main/java/com/election/backendjava/services/ReportingUnitService.java
package com.election.backendjava.services;

import com.election.backendjava.ReportingUnit;
import com.election.backendjava.repositories.ReportingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingUnitService {

    @Autowired
    private ReportingUnitRepository reportingUnitRepository;

    public List<ReportingUnit> findByManagingAuthority_AuthorityIdentifier(String authorityIdentifier) {
        return reportingUnitRepository.findByManagingAuthority_AuthorityIdentifier(authorityIdentifier);
    }
}