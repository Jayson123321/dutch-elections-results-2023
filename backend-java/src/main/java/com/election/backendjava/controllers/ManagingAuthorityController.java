package com.election.backendjava.controllers;

import com.election.backendjava.PartyVotes;
import com.election.backendjava.entities.ManagingAuthority;
import com.election.backendjava.repositories.ManagingAuthorityRepository;
import com.election.backendjava.repositories.PartyVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managing-authorities")
public class ManagingAuthorityController {

    @Autowired
    private ManagingAuthorityRepository managingAuthorityRepository;

    @Autowired
    private PartyVotesRepository partyVotesRepository;

    @GetMapping
    public List<ManagingAuthority> getAllManagingAuthorities() {
        return managingAuthorityRepository.findAll();
    }

    @GetMapping("/{authorityIdentifier}/party-votes")
    public List<PartyVotes> getPartyVotesByAuthorityIdentifier(@PathVariable("authorityIdentifier") String authorityIdentifier) {
        return partyVotesRepository.findByReportingUnitId(authorityIdentifier);
    }

}

