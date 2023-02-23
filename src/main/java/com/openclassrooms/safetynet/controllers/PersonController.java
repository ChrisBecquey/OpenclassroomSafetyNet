package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.DTO.CommunityEmailDTO;
import com.openclassrooms.safetynet.DTO.PhoneAlertDTO;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.FirestationService;
import com.openclassrooms.safetynet.services.PersonService;
import com.openclassrooms.safetynet.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;
    @Autowired
    FirestationService firestationService;


    @PostMapping(value = "/person")
    public void savePerson(@RequestBody Person person) {
        personService.add(person);
    }
}


// Refactor controller + service
// Reprendre le TDD
