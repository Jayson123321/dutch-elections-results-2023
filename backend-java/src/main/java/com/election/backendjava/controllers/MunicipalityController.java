package com.election.backendjava.controllers;

import com.election.backendjava.entities.Municipality;
import com.election.backendjava.services.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/municipalities")
public class MunicipalityController {

    @Autowired
    private MunicipalityService municipalityService;

    @GetMapping
    public List<Municipality> getAllMunicipalities() {
        return municipalityService.getAllMunicipalities();
    }
}
