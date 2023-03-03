package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private DatabaseService databaseService;

    public List<Person> findAll() {
        return databaseService.getPersons();
    }
    public List<Person> getPersonsByLastName(String lastName) {
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
        databaseService.getPersons().add(person);
        return true;
    }

    public Boolean delete(Person person) {
        databaseService.getPersons().remove(person);
        return true;
    }

    public Person update(Person person) {
        int index = databaseService.getPersons().indexOf(getPersonByFirstAndLastName(person.getFirstName(), person.getLastName()));
        return databaseService.getPersons().set(index, person);
    }



}
