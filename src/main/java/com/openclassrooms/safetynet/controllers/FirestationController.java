package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.FirestationService;
import com.openclassrooms.safetynet.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FirestationController {
    @Autowired
    private FirestationService firestationService;
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getPersonsInFirestationRange(@RequestParam int station) {
        List<Firestation> firestationsByNumber = firestationService.getFirestationByNumber(station);
        return new ArrayList<>(personService.getPersonsByAdress(firestationsByNumber.get(0).getAddress()));
    }
}
