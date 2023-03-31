package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLParagraphElement;

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
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {
        if(personService.update(person))
            return ResponseEntity.status(HttpStatus.OK).body("Person updated");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The person you want to update dont exist");
    }

    @DeleteMapping("/person")
    public ResponseEntity<String> detetePerson(@RequestParam String firstName, String lastName) {
        if(personService.delete(personService.getPersonByFirstAndLastName(firstName, lastName)))
            return ResponseEntity.status(HttpStatus.OK).body("Person deleted");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No person found");
    }
}

