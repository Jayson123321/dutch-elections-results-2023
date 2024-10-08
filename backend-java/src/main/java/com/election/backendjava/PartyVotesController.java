package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/party-votes")
public class PartyVotesController {

    @Autowired
    private PartyVotesRepository partyVotesRepository;

    @GetMapping
    public Page<PartyVotes> getAllPartyVotes(Pageable pageable) {
        return partyVotesRepository.findAll(pageable);
    }
}
