package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    @GetMapping(value = "/persons")
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @GetMapping(value = "/person")
    public Person getPersonByFirstAndLastName(@RequestParam String firstName, String lastName) {
        return personService.getPersonByFirstAndLastName(firstName, lastName);
    }

    @PostMapping(value = "/person")
    public ResponseEntity<String> savePerson(@RequestBody Person person) {
        if(personService.save(person))
            return ResponseEntity.status(HttpStatus.CREATED).body("Person created");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Person already exist");
    }

    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person person) {
        return personService.update(person);
    }

    @DeleteMapping("/person")
    public Boolean detetePerson(@RequestParam String firstName, String lastName) {
        return personService.delete(personService.getPersonByFirstAndLastName(firstName, lastName));
    }
}

