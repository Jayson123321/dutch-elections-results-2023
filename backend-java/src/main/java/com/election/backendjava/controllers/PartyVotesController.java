// File: backend-java/src/main/java/com/election/backendjava/PartyVotesController.java
package com.election.backendjava;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/party-votes")
public class PartyVotesController {

    @Autowired
    private PartyVotesRepository partyVotesRepository;

    @GetMapping("/reporting-unit/{reportingUnitId}")
    public List<PartyVotes> getPartyVotesByReportingUnit(@PathVariable String reportingUnitId) {
        return partyVotesRepository.findByReportingUnitId(reportingUnitId);
    }
}