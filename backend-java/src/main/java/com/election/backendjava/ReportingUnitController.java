package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reporting-units")
public class ReportingUnitController {

    @Autowired
    private ReportingUnitRepository reportingUnitRepository;

    @GetMapping
    public List<ReportingUnit> getAllReportingUnits() {
        return reportingUnitRepository.findAll();
    }
}