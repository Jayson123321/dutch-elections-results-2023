package com.election.backendjava.controllers;

import com.election.backendjava.entities.UnbanRequest;
import com.election.backendjava.services.UnbanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/unban-requests")
public class UnbanRequestController {

    @Autowired
    private UnbanRequestService unbanRequestService;

    @GetMapping("/all")
    public List<UnbanRequest> getAllUnbanRequests() {
        return unbanRequestService.getAllUnbanRequests();
    }
}
