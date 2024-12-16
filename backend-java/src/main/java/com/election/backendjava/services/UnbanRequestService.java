package com.election.backendjava.services;

import com.election.backendjava.entities.UnbanRequest;
import com.election.backendjava.repositories.UnbanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnbanRequestService {

    @Autowired
    private UnbanRequestRepository unbanRequestRepository;

    public List<UnbanRequest> getAllUnbanRequests() {
        return unbanRequestRepository.findAll();
    }
}
