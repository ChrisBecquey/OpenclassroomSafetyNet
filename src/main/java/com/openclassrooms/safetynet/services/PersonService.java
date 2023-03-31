package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    Logger logger = LoggerFactory.getLogger(PersonService.class);
    @Autowired
    private DatabaseService databaseService;

    public List<Person> findAll() {
        return databaseService.getPersons();
    }
    public List<Person> getPersonsByLastName(String lastName) {
        logger.info("Entré dans la méthode");
        return databaseService.getPersons().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .toList();
    }

    public Person getPersonByFirstAndLastName(String firstName, String lastName) {
        return databaseService.getPersons()
                .stream()
                .filter(person -> person.getLastName().equals(lastName) && person.getFirstName().equals(firstName))
                .reduce((a, b) -> {
                    throw  new IllegalStateException("Multiple elements: " + a + "," + b);
                })
                .orElse(null);
    }
    public List<Person> getPersonsByAdress(String adress) {
        return databaseService.getPersons().stream()
                .filter(person -> person.getAddress().equals(adress))
                .toList();
    }

    public List<Person> getPersonsByCity(String city) {
        return databaseService.getPersons().stream()
                .filter(person -> person.getCity().equals(city))
                .toList();
    }

    public Boolean save(Person person) {
        if (this.getPersonByFirstAndLastName(person.getFirstName(), person.getLastName()) == null) {
            databaseService.getPersons().add(person);
            return true;
        }
        return false;
    }

    public Boolean delete(Person person) {
        if (this.getPersonByFirstAndLastName(person.getFirstName(), person.getLastName()) != null) {
            databaseService.getPersons().remove(person);
            return true;
        }
        return false;
    }

    public Boolean update(Person person) {
        if (this.getPersonByFirstAndLastName(person.getFirstName(), person.getLastName()) != null) {
            int index = databaseService.getPersons().indexOf(getPersonByFirstAndLastName(person.getFirstName(), person.getLastName()));
            databaseService.getPersons().set(index, person);
            return true;
        }
        return false;
    }
}
