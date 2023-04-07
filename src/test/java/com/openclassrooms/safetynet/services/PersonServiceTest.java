package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonServiceTest {
    @InjectMocks
    PersonService personService;
    @Mock
    DatabaseService databaseService;
    List<Person> persons = new ArrayList<>();


    @BeforeEach
    void init() {
        Person person1 = new Person();
        person1.setFirstName("Louis");
        person1.setLastName("Dupont");
        person1.setEmail("Louis.dupont@gmail.com");
        person1.setAddress("10 rue de la croix");
        person1.setPhone("888-666-2233");
        person1.setZip("59000");
        person1.setCity("Lille");
        persons.add(person1);

        Person person2 = new Person();
        person2.setFirstName("Albert");
        person2.setLastName("Lambert");
        person2.setAddress("12 rue du fou");
        person2.setEmail("Albert.dupont@gmail.com");
        person2.setPhone("777-555-1122");
        person2.setZip("31000");
        person2.setCity("Toulouse");
        persons.add(person2);

        Person person3 = new Person();
        person3.setFirstName("Jeanne");
        person3.setLastName("Lambert");
        person3.setAddress("12 rue du fou");
        person3.setEmail("Jeanne.lambert@gmail.com");
        person3.setPhone("999-555-1133");
        person3.setZip("31000");
        person3.setCity("Toulouse");
        persons.add(person3);
    }

    @Test
    void findAll() {
        when(databaseService.getPersons()).thenReturn(persons);
        List<Person> allPersons = personService.findAll();
        assertEquals(3, allPersons.size());
    }

    @Test
    void getPersonsByLastName() {
        when(databaseService.getPersons()).thenReturn(persons);
        List<Person> listTest = personService.getPersonsByLastName("Dupont");
        assertEquals(1, listTest.size());
        assertEquals("Dupont", listTest.get(0).getLastName());
    }

    @Test
    void shouldEmptyList_whenNoPersonWithThisLastName() {
        when(databaseService.getPersons()).thenReturn(persons);
        List<Person> listTest = personService.getPersonsByLastName("Loic");
        assertEquals(0, listTest.size());
    }

    @Test
    void getPersonsByAdress() {
        when(databaseService.getPersons()).thenReturn(persons);
        List<Person> personsByAdress = personService.getPersonsByAdress("12 rue du fou");
        assertEquals(2, personsByAdress.size());
    }

    @Test
    void getPersonsByCity() {
        when(databaseService.getPersons()).thenReturn(persons);
        List<Person> personsByCity = personService.getPersonsByCity("Toulouse");
        assertEquals(2, personsByCity.size());
    }

    @Test
    void addPerson() {
        when(databaseService.getPersons()).thenReturn(persons);
        Person personTest = new Person();
        personTest.setFirstName("Louise");
        personTest.setLastName("Reginald");
        personTest.setAddress("15 rue du pont");
        personTest.setEmail("Louise.reginald@gmail.com");
        personTest.setPhone("888-555-1133");
        personTest.setZip("31000");
        personTest.setCity("Toulouse");

        personService.save(personTest);
        assertEquals(4, databaseService.getPersons().size());
    }

    @Test
    void removePerson() {
        when(databaseService.getPersons()).thenReturn(persons);
        Person personToRemove = personService.getPersonByFirstAndLastName("Jeanne", "Lambert");
        personService.delete(personToRemove);
        assertEquals(2, databaseService.getPersons().size());
    }

    @Test
    void shouldNotRemovePerson_whenPersonIsNull() {
        when(databaseService.getPersons()).thenReturn(persons);
        personService.delete(null);
        assertEquals(3, persons.size());
    }

    @Test
    void updatePerson() {
        when(databaseService.getPersons()).thenReturn(persons);
        Person personToUpdate = personService.getPersonByFirstAndLastName("Jeanne", "Lambert");
        personToUpdate.setEmail("j.lambert@ymail.com");
        personToUpdate.setPhone("888-999-888");
        personService.update(personToUpdate);
        assertEquals(3, databaseService.getPersons().size());
        assertEquals("j.lambert@ymail.com", databaseService.getPersons().get(2).getEmail());
        assertEquals("888-999-888", databaseService.getPersons().get(2).getPhone());
    }

    @Test
    void shouldReturnNull_whenPersonIsNotFound() {
        when(databaseService.getPersons()).thenReturn(persons);
        Person notFound = personService.getPersonByFirstAndLastName("toto", "tati");
        assertNull(notFound);

    }

    @Test
    void getPersonByFirstAndLastName() {
        when(databaseService.getPersons()).thenReturn(persons);
        Person jeanneLambert = personService.getPersonByFirstAndLastName("Jeanne", "Lambert");
        assertEquals("Lambert", jeanneLambert.getLastName());
        assertEquals("Jeanne", jeanneLambert.getFirstName());
    }

}