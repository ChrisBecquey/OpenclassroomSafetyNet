package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void getAllPersons() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
    }

    @Test
    void getPersonByFirstAndLastName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persons?firstName={firstName}&lastName={lastName}", "John", "Boyd"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void savePerson() throws Exception {
        Person person1 = new Person();
        person1.setFirstName("Jean");
        person1.setLastName("Michel");
        person1.setEmail("Louis.dupont@gmail.com");
        person1.setAddress("10 rue de la croix");
        person1.setPhone("888-666-2233");
        person1.setZip("59000");
        person1.setCity("Lille");

        String personJson = new ObjectMapper().writeValueAsString(person1);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personJson))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updatePerson() {
    }

    @Test
    void detetePerson() {
    }
}