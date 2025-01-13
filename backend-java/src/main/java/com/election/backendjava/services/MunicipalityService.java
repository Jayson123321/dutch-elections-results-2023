package com.election.backendjava.services;

import com.election.backendjava.entities.Municipality;
import com.election.backendjava.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipalityService {

    @Autowired
    MunicipalityRepository municipalityRepository;

    public List<Municipality> getAllMunicipalities() {
        return municipalityRepository.findAll();
    }

    public Municipality getMunicipalityById(Long id) {
        return municipalityRepository.findById(id).orElse(null);
    }
}
