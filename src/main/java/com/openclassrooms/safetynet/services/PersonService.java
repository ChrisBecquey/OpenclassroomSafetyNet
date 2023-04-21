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
        logger.info("Get person by lastname={}", lastName); //
        return databaseService.getPersons().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .toList();
    }

    public Person getPersonByFirstAndLastName(String firstName, String lastName) {
        logger.info("Get person by firstName={} and lastName={}", firstName, lastName);
        return databaseService.getPersons()
                .stream()
                .filter(person -> person.getLastName().equals(lastName) && person.getFirstName().equals(firstName))
                .reduce((a, b) -> {
                    throw  new IllegalStateException("Multiple elements: " + a + "," + b);
                })
                .orElse(null);
    }
    public List<Person> getPersonsByAdress(String adress) {
        logger.info("Get all the persons at the adress: {}", adress);
        return databaseService.getPersons().stream()
                .filter(person -> person.getAddress().equals(adress))
                .toList();
    }

    public List<Person> getPersonsByCity(String city) {
        logger.info("Get all the persons living in this city: {}", city);
        return databaseService.getPersons().stream()
                .filter(person -> person.getCity().equals(city))
                .toList();
    }

    public Boolean save(Person person) {
        if (person != null && this.getPersonByFirstAndLastName(person.getFirstName(), person.getLastName()) == null) {
            databaseService.getPersons().add(person);
            logger.info("New person add to the base");
            return true;
        }
        logger.warn("Already have a person with this name and lastName");
        return false;
    }

    public Boolean delete(Person person) {
        if (person != null && this.getPersonByFirstAndLastName(person.getFirstName(), person.getLastName()) != null) {
            databaseService.getPersons().remove(person);
            logger.info("This person is delete from db");
            return true;
        }
        logger.warn("Cant delete this person cause we didnt find her");
        return false;
    }

    public Boolean update(Person person) {
        if (person != null && this.getPersonByFirstAndLastName(person.getFirstName(), person.getLastName()) != null) {
            int index = databaseService.getPersons().indexOf(getPersonByFirstAndLastName(person.getFirstName(), person.getLastName()));
            databaseService.getPersons().set(index, person);
            logger.info("Update done for this person");
            return true;
        }
        logger.warn("The person you are trying to update do not exist");
        return false;
    }
}
