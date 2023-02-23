package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Database;
import com.openclassrooms.safetynet.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseServiceTest {

    DatabaseService databaseService;

    @BeforeEach
    void init() {
        List<Person> persons = new ArrayList<>();

        Person person1 = new Person();
        person1.setFirstName("Louis");
        person1.setLastName("Roger");
        person1.setEmail("Louis.roger@gmail.com");
        person1.setPhone("888-666-2233");
        person1.setZip("59000");
        person1.setCity("Lille");
        persons.add(person1);

        Person person2 = new Person();
        person2.setFirstName("Albert");
        person2.setLastName("Dupont");
        person2.setEmail("Albert.dupont@gmail.com");
        person2.setPhone("777-555-1122");
        person2.setZip("31000");
        person2.setCity("Toulouse");
        persons.add(person2);

        Database database = new Database();
        database.setPersons((ArrayList<Person>) persons);
        databaseService = new DatabaseService();
        databaseService.setDatabase(database);
    }

    @Test
    void getPersons() {
        List<Person> all = databaseService.getPersons();
        assertThat(all.size()).isNotNull();
        assertThat(all.size()).isEqualTo(2);
    }
}
