package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[5].firstName", is("Jonanathan")));
    }

    @Test
    public void savePersons() throws Exception{
        Person person1 = new Person();
        person1.setFirstName("Jean");
        person1.setLastName("Michel");
        person1.setEmail("Louis.dupont@gmail.com");
        person1.setAddress("10 rue de la croix");
        person1.setPhone("888-666-2233");
        person1.setZip("59000");
        person1.setCity("Lille");

        String personJson = new ObjectMapper().writeValueAsString(person1);

        Person person2 = new Person();
        person2.setFirstName("Jean");
        person2.setLastName("Michel");
        person2.setEmail("Louis.dupont@gmail.com");
        person2.setAddress("10 rue de la croix");
        person2.setPhone("888-666-2233");
        person2.setZip("59000");
        person2.setCity("Lille");

        String personJson2 = new ObjectMapper().writeValueAsString(person2);

        //First save, the person dont exist on db, should save
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isCreated())
                .andReturn();

        //Second save, the person already exist, should not save
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personJson2))
                .andExpect(status().isConflict())
                .andReturn();

    }

    @Test
    public void shouldDeletePerson() throws Exception {
        Person person1 = new Person();
        person1.setFirstName("Louis");
        person1.setLastName("Boyd");
        person1.setEmail("Louis.boyd@gmail.com");
        person1.setAddress("10 rue de la croix");
        person1.setPhone("888-666-2233");
        person1.setZip("59000");
        person1.setCity("Lille");

        String personJson = new ObjectMapper().writeValueAsString(person1);

        //Save a person in first place
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isCreated())
                .andReturn();

        //Delete the person we add and see if it's work
        mockMvc.perform(delete("/person?firstName={firstName}&lastName={lastName}", "Louis", "Boyd"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/person?firstName={firstName}&lastName={lastName}", "Jean", ""))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdatePerson() throws Exception {
        Person person1 = new Person();
        person1.setFirstName("Jean");
        person1.setLastName("Michel");
        person1.setEmail("Louis.boyd@gmail.com");
        person1.setAddress("10 rue de la croix");
        person1.setPhone("888-666-2233");
        person1.setZip("59000");
        person1.setCity("Lille");

        String personJson = new ObjectMapper().writeValueAsString(person1);

        Person person2 = new Person();
        person2.setFirstName("Jean");
        person2.setLastName("Michel");
        person2.setEmail("Jean.michel@gmail.com");
        person2.setAddress("18 rue de la croix");
        person2.setPhone("999-666-2233");
        person2.setZip("21000");
        person2.setCity("Dijon");

        String personJson2 = new ObjectMapper().writeValueAsString(person2);

        //Save a person in first place
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isCreated())
                .andReturn();

        //Update the person
        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personJson2))
                .andExpect(status().isOk());
    }

}
