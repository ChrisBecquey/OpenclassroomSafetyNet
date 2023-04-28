package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.services.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FirestationController.class)
class FirestationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

    @Test
    void getAllFirestations() throws Exception {
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk());
    }

    @Test
    void getFirestationsByNumberAndAdress() throws Exception {
        mockMvc.perform(get("/firestation?number={number}&adress={adress}", "1", "644 Gershwin Cir"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void shouldSaveFirestation() throws Exception {
        Firestation firestation1 = new Firestation();
        firestation1.setAddress("1509 Culver St");
        firestation1.setStation(1);

        String firestationJson = new ObjectMapper().writeValueAsString(firestation1);
        when(firestationService.addFirestation(any())).thenReturn(true);

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(firestationJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void shouldNotSaveFirestation_whenAlreadyExist() throws Exception {
        Firestation firestation1 = new Firestation();
        firestation1.setAddress("1509 Culver St");
        firestation1.setStation(1);

        String firestationJson = new ObjectMapper().writeValueAsString(firestation1);
        when(firestationService.addFirestation(any())).thenReturn(false);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firestationJson))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    void shouldDeleteFirestation() throws Exception {
        when(firestationService.deleteFirestation(any())).thenReturn(true);

        mockMvc.perform(delete("/firestation?number={number}&adress={adress}", "1", "644 Gershwin Cir"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteFirestation_whenFirestationNotFound() throws Exception {
        when(firestationService.deleteFirestation(any())).thenReturn(false);

        mockMvc.perform(delete("/firestation?number={number}&adress={adress}", "1", "644 Gershwin Cir"))
                .andExpect(status().isNotFound());
    }
}