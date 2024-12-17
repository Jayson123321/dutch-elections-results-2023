package com.election.backendjava.controllers;

import com.election.backendjava.entities.UnbanRequest;
import com.election.backendjava.services.UnbanRequestService;
import com.election.backendjava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/unban-requests")
public class UnbanRequestController {

    @Autowired
    private UnbanRequestService unbanRequestService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UnbanRequest> getAllUnbanRequests() {
        return unbanRequestService.getAllUnbanRequests();
    }

    @PutMapping("/{id}/approve")
    public void approveUnbanRequest(@PathVariable Long id) {
        UnbanRequest unbanRequest = unbanRequestService.findById(id);
        if (unbanRequest != null) {
            userService.unbanUser(unbanRequest.getUser().getId());
            unbanRequestService.deleteUnbanRequest(id);
        }
    }


}
