package com.election.backendjava.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.election.backendjava.entities.Municipality;
import com.election.backendjava.services.MunicipalityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class MunicipalityServiceTest {

    @Mock
    private MunicipalityService municipalityService;

    @InjectMocks
    private MunicipalityServiceTest municipalityServiceTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMunicipalities() {
        Municipality municipality = new Municipality();
        municipality.setName("Gemeente A");
        List<Municipality> municipalities = Arrays.asList(municipality);

        when(municipalityService.getAllMunicipalities()).thenReturn(municipalities);

        List<Municipality> result = municipalityService.getAllMunicipalities();
        assertEquals("Gemeente A", result.get(0).getName());
    }
}