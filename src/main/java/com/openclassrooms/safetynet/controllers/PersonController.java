package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.FirestationService;
import com.openclassrooms.safetynet.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;
    @Autowired
    FirestationService firestationService;

    @GetMapping(value = "/persons")
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @GetMapping(value = "/person")
    public Person getPersonByFirstAndLastName(@RequestParam String firstName, String lastName) {
        return personService.getPersonByFirstAndLastName(firstName, lastName);
    }

    @PostMapping(value = "/person")
    public void savePerson(@RequestBody Person person) {
        personService.save(person);
    }

    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person person) {
        return personService.update(person); //pb sur retour postman pbbmtn mauvaise méthode de retour on retrouve 2 Pauline => Problème
    }

    @DeleteMapping("/person")
    public Boolean detetePerson(@RequestParam String firstName, String lastName) {
        return personService.delete(personService.getPersonByFirstAndLastName(firstName, lastName));
    }
}

