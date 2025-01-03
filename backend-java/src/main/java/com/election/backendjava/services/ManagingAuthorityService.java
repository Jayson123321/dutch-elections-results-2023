// src/main/java/com/election/backendjava/services/ManagingAuthorityService.java
package com.election.backendjava.services;

import com.election.backendjava.entities.ManagingAuthority;
import com.election.backendjava.repositories.ManagingAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagingAuthorityService {

    @Autowired
    private ManagingAuthorityRepository managingAuthorityRepository;

    public List<ManagingAuthority> findAll() {
        return managingAuthorityRepository.findAll();
    }
}