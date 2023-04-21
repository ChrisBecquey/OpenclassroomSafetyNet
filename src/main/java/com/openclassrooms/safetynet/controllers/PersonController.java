package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;
    Logger logger = LoggerFactory.getLogger(PersonController.class);


    @GetMapping(value = "/persons")
    public List<Person> getAllPersons() {
        logger.info("Get the list off all persons in db");
        return personService.findAll();
    }

    @GetMapping(value = "/person")
    public Person getPersonByFirstAndLastName(@RequestParam String firstName, String lastName) {
        logger.info("Get the person with firstName={} and lastName={}", firstName, lastName);
        return personService.getPersonByFirstAndLastName(firstName, lastName);
    }

    @PostMapping(value = "/person")
    public ResponseEntity<String> savePerson(@RequestBody Person person) {
        if (personService.save(person)) {
            logger.info("Person created: {}", person);
            return ResponseEntity.status(HttpStatus.CREATED).body("Person created");
        } else {
            logger.warn("Person already exists: {}", person);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Person already exist");
        }
    }

    @PutMapping("/person")
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {
        if (personService.update(person)) {
            logger.info("Person updated: {}", person);
            return ResponseEntity.status(HttpStatus.OK).body("Person updated");
        } else {
            logger.warn("This person don't exists: {}", person);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The person you want to update dont exist");
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<String> detetePerson(@RequestParam String firstName, String lastName) {
        if (personService.delete(personService.getPersonByFirstAndLastName(firstName, lastName))) {
            logger.info("Person deleted");
            return ResponseEntity.status(HttpStatus.OK).body("Person deleted");
        } else {
            logger.warn("Person not found: firstName={}, lastName={}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No person found");
        }
    }
}

