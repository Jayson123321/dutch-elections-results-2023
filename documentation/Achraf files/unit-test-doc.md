# Testing MunicipalityService

In deze doc leg ik uit hoe ik een unit test heb opgezet voor de MunicipalityService. Het doel hiervan was door JUnit en Mockito te testen of de service werkt door methodes uit te voeren en te testen.

## Opzetten unit test

Hiervoor heb ik een nieuwe class aangemaakt genaamd MunicipalityServiceTest.java.
```java
package com.election.backendjava.services;

import com.election.backendjava.entities.Municipality;
import com.election.backendjava.repositories.MunicipalityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class MunicipalityServiceTest {

    @Test
    void testGetAllMunicipalities() {
        // Mockt repository
        MunicipalityRepository municipalityRepository = Mockito.mock(MunicipalityRepository.class);

        // dit maakt een service met de mock repository
        MunicipalityService municipalityService = new MunicipalityService();
        municipalityService.municipalityRepository = municipalityRepository;

        // dit is voor de voorbeelddata
        Municipality mun1 = new Municipality();
        mun1.setId(1L);
        mun1.setMunicipalityName("Gemeente A");

        Municipality mun2 = new Municipality();
        mun2.setId(2L);
        mun2.setMunicipalityName("Gemeente B");

        List<Municipality> mockMunicipalities = Arrays.asList(mun1, mun2);

        // dit stelt de mock repository in om deze data terug te geven
        Mockito.when(municipalityRepository.findAll()).thenReturn(mockMunicipalities);

        // roept de methode aan
        List<Municipality> result = municipalityService.getAllMunicipalities();

        // checkt of het resultaat klopt
        assertEquals(2, result.size());
        assertEquals("Gemeente A", result.get(0).getMunicipalityName());
        assertEquals("Gemeente B", result.get(1).getMunicipalityName());
    }

    @Test
    void testGetMunicipalityById_NotFound() {
        // mockt repository
        MunicipalityRepository municipalityRepository = Mockito.mock(MunicipalityRepository.class);

        // dit maakt een service met de mock repository
        MunicipalityService municipalityService = new MunicipalityService();
        municipalityService.municipalityRepository = municipalityRepository;

        // Stel in dat de repository een lege Optional teruggeeft (Arrange)
        when(municipalityRepository.findById(1L)).thenReturn(Optional.empty());

        // Roep de metode aan (act)
        Municipality result = municipalityService.getMunicipalityById(1L);

        // controleert of het resultaat null is (assert)
        assertNull(result);
    }
}
```

## Belangrijke punten
- Ik maak gebruik van mockito om dependencies zoals de repository na te bootsen
- Zorg ervoor dat de service werkt met de repository die gebruikt wordt

## Test methodes
- testGetAllMunicipalities: Test of de methode alle gemeenten correct ophaalt uit db en teruggeeft.
- testGetMunicipalityById_NotFound: Test of de methode null teruggeeft wanneer een gemeente niet wordt gevonden.

## Conclusie
Door deze unit test kan je ervoor zorgen dat de MunicipalityService goed functioneert in verschillende situaties. Deze test doen situaties na om succesvolle en niet succesvolle scenario's te checken. Dit helpt goed om fouten te voorkomen.