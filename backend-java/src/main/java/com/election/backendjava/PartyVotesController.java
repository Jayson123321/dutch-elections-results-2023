package com.election.backendjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/party-votes")
public class PartyVotesController {

    @Autowired
    private PartyVotesRepository partyVotesRepository;

    @GetMapping
    public List<PartyVotes> getAllPartyVotes() {
        return partyVotesRepository.findAll();
    }

}
