package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }

    public Person getPersonByFirstAndLastName(String firstName, String lastName) {
        return databaseService.getPersons()
                .stream()
                .filter(person -> person.getLastName().equals(lastName) && person.getFirstName().equals(firstName))
                .reduce((a, b) -> {
                    throw  new IllegalStateException("Multiple elements: " + a + "," + b);
                })
                .get();
    }
    public List<Person> getPersonsByAdress(String adress) {
        return databaseService.getPersons().stream()
                .filter(person -> person.getAddress().equals(adress))
                .collect(Collectors.toList());
    }

    public List<Person> getPersonsByCity(String city) {
        return databaseService.getPersons().stream()
                .filter(person -> person.getCity().equals(city))
                .collect(Collectors.toList());
    }

    public Boolean save(Person person) {   //thrown ==> Exception si bien passé on renvoie qqch / autrement
        databaseService.getPersons().add(person);
        return true; //vérifier qu'on est pas 2 fois la meme personne. (Meme nom / prenom)
    }

    public Boolean delete(Person person) {
        databaseService.getPersons().remove(person);
        return true;
    }

}
