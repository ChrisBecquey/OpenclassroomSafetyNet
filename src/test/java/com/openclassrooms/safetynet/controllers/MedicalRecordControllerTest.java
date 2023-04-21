package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.models.MedicalRecord;
import com.openclassrooms.safetynet.services.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    void getAllMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecords"))
                .andExpect(status().isOk());
    }

    @Test
    void getMedicalRecordByFirstAndLastName() throws Exception {
        mockMvc.perform(get("/medicalRecord?firstName={firstName}&lastName={lastName}", "John", "Boyd"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void shouldSaveMedicalRecord() throws Exception {
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Louis");
        medicalRecord1.setLastName("Boyd");
        medicalRecord1.setBirthdate("03/06/2010");
        List<String> medications1 = new ArrayList<>();
        List<String> listMedications1 = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        medications1.addAll(listMedications1);
        medicalRecord1.setMedications(medications1);
        List<String> allergies1 = new ArrayList<>();
        List<String> listAllergies1 = Arrays.asList("peanut", "shellfish", "aznol");
        allergies1.addAll(listAllergies1);
        medicalRecord1.setAllergies(allergies1);

        String medicalRecordJson = new ObjectMapper().writeValueAsString(medicalRecord1);
        when(medicalRecordService.addMedicalRecord(any())).thenReturn(true);

        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(medicalRecordJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void shouldNotSaveMedicalRecord_whenAlreadyExist() throws Exception {
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Louis");
        medicalRecord1.setLastName("Boyd");
        medicalRecord1.setBirthdate("03/06/2010");
        List<String> medications1 = new ArrayList<>();
        List<String> listMedications1 = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        medications1.addAll(listMedications1);
        medicalRecord1.setMedications(medications1);
        List<String> allergies1 = new ArrayList<>();
        List<String> listAllergies1 = Arrays.asList("peanut", "shellfish", "aznol");
        allergies1.addAll(listAllergies1);
        medicalRecord1.setAllergies(allergies1);

        String medicalRecordJson = new ObjectMapper().writeValueAsString(medicalRecord1);
        when(medicalRecordService.addMedicalRecord(any())).thenReturn(false);

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(medicalRecordJson))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    void shouldDeleteMedicalRecord() throws Exception {
        when(medicalRecordService.deleteMedicalRecord(any())).thenReturn(true);

        mockMvc.perform(delete("/medicalRecord?firstName={firstName}&lastName{lastName}", "Jean", "Michel"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSendErrorOnDelete_whenMedicalRecordIsNotFound() throws Exception {
        when(medicalRecordService.deleteMedicalRecord(any())).thenReturn(false);

        mockMvc.perform(delete("/medicalRecord?firstName={firstName}&lastName{lastName}", "Jean", "Michel"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateMedicalRecord() throws Exception {
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Louis");
        medicalRecord1.setLastName("Boyd");
        medicalRecord1.setBirthdate("03/06/2010");
        List<String> medications1 = new ArrayList<>();
        List<String> listMedications1 = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        medications1.addAll(listMedications1);
        medicalRecord1.setMedications(medications1);
        List<String> allergies1 = new ArrayList<>();
        List<String> listAllergies1 = Arrays.asList("peanut", "shellfish", "aznol");
        allergies1.addAll(listAllergies1);
        medicalRecord1.setAllergies(allergies1);

        String medicalRecordJson = new ObjectMapper().writeValueAsString(medicalRecord1);
        when(medicalRecordService.updateMedicalRecord(any())).thenReturn(true);

        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(medicalRecordJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotUpdateMedicalRecord_whenNotFound() throws Exception {
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Louis");
        medicalRecord1.setLastName("Boyd");
        medicalRecord1.setBirthdate("03/06/2010");
        List<String> medications1 = new ArrayList<>();
        List<String> listMedications1 = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        medications1.addAll(listMedications1);
        medicalRecord1.setMedications(medications1);
        List<String> allergies1 = new ArrayList<>();
        List<String> listAllergies1 = Arrays.asList("peanut", "shellfish", "aznol");
        allergies1.addAll(listAllergies1);
        medicalRecord1.setAllergies(allergies1);

        String medicalRecordJson = new ObjectMapper().writeValueAsString(medicalRecord1);
        when(medicalRecordService.updateMedicalRecord(any())).thenReturn(false);

        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(medicalRecordJson))
                .andExpect(status().isNotFound());
    }
}