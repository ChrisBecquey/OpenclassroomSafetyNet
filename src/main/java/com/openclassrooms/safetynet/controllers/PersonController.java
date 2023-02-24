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
    public Boolean updatePerson(@RequestParam String firstName, String lastName, @RequestBody Person person) {
        Person existingPerson = personService.getPersonByFirstAndLastName(firstName, lastName);
        existingPerson.setFirstName(person.getFirstName());
        existingPerson.setLastName(person.getLastName());
        existingPerson.setAddress(person.getAddress());
        existingPerson.setCity(person.getCity());
        existingPerson.setZip(person.getZip());
        existingPerson.setPhone(person.getPhone());
        existingPerson.setEmail(person.getEmail());
        return personService.save(existingPerson); //pb sur retour postman pbbmtn mauvaise méthode de retour on retrouve 2 Pauline => Problème
    }

    @DeleteMapping("/person")
    public Boolean detetePerson(@RequestParam String firstName, String lastName) {
        Person existingPerson = personService.getPersonByFirstAndLastName(firstName, lastName);
        return personService.delete(existingPerson);
    }
}

