package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.FirestationService;
import com.openclassrooms.safetynet.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/firestations")
    public List<Firestation> getAllFirestations() {
        return firestationService.getAllFirestations();
    }

    @GetMapping("/firestation")
    public Firestation getFirestationByNumberAndAdress(@RequestParam int number, String adress) {
        return firestationService.getFirestationByNumberAndAdress(number, adress);
    }
    @PostMapping("/firestation")
    public void saveFirestation(@RequestBody Firestation firestation) {
        firestationService.addFirestation(firestation);
    }

    @PutMapping("/firestation")
    public Firestation updateFirestation(@RequestBody Firestation firestation) {
        return firestationService.updateFirestation(firestation);
    }

    @DeleteMapping("/firestation")
    public Boolean deleteFirestation(@RequestParam int number, String adress) {
        return firestationService.deleteFirestation(firestationService.getFirestationByNumberAndAdress(number, adress));
    }

}
