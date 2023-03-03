package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.MedicalRecord;
import com.openclassrooms.safetynet.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.print.attribute.standard.Media;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MedicalRecordServiceTest {
    @InjectMocks
    MedicalRecordService medicalRecordService;

    @Mock
    DatabaseService databaseService;
    List<MedicalRecord> medicalRecords = new ArrayList<>();

    @BeforeEach
    void init() {
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

        medicalRecords.add(medicalRecord1);

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jeanne");
        medicalRecord2.setLastName("Boyd");
        medicalRecord2.setBirthdate("03/06/1990");
        List<String> medications2 = new ArrayList<>();
        List<String> listMedications2 = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        medications2.addAll(listMedications2);
        medicalRecord2.setMedications(medications2);
        List<String> allergies2 = new ArrayList<>();
        List<String> listAllergies2 = Arrays.asList("peanut", "shellfish", "aznol");
        allergies2.addAll(listAllergies2);
        medicalRecord2.setAllergies(allergies2);

        medicalRecords.add(medicalRecord2);


        MedicalRecord medicalRecord3 = new MedicalRecord();
        medicalRecord3.setFirstName("David");
        medicalRecord3.setLastName("Dupont");
        medicalRecord3.setBirthdate("03/06/2015");
        List<String> medications3 = new ArrayList<>();
        List<String> listMedications3 = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        medications3.addAll(listMedications3);
        medicalRecord3.setMedications(medications3);
        List<String> allergies3 = new ArrayList<>();
        List<String> listAllergies3 = Arrays.asList("peanut", "shellfish", "aznol");
        allergies3.addAll(listAllergies3);
        medicalRecord3.setAllergies(allergies3);

        medicalRecords.add(medicalRecord3);
    }

    @Test
    void getAllMedicalRecords() {
        when(databaseService.getMedicalRecords()).thenReturn(medicalRecords);
        List<MedicalRecord> allMedicalRecords = medicalRecordService.findAll();
        assertEquals(3, allMedicalRecords.size());
    }
    @Test
    void getMedicalRecordsForChild() throws ParseException {
        when(databaseService.getMedicalRecords()).thenReturn(medicalRecords);
        List<MedicalRecord> childMedicalRecords = medicalRecordService.getMedicalRecordsForChild();
        assertEquals(2, childMedicalRecords.size());
    }

    @Test
    void getMedicalRecordsFromFirstAndLastNameForChild() {
        when(databaseService.getMedicalRecords()).thenReturn(medicalRecords);
        List<MedicalRecord> medicalRecordsFromFirstAndLastName = medicalRecordService.getMedicalRecordsFromFirstAndLastNameForChild("Louis", "Boyd");
        assertEquals(1, medicalRecordsFromFirstAndLastName.size());
    }

    @Test
    void addMedicalRecord() {
        when(databaseService.getMedicalRecords()).thenReturn(medicalRecords);

        MedicalRecord medicalRecordToAdd = new MedicalRecord();
        medicalRecordToAdd.setFirstName("Louise");
        medicalRecordToAdd.setLastName("Dubois");
        medicalRecordToAdd.setBirthdate("03/06/1990");
        List<String> medications = new ArrayList<>();
        List<String> listMedications = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        medications.addAll(listMedications);
        medicalRecordToAdd.setMedications(medications);
        List<String> allergies = new ArrayList<>();
        List<String> listAllergies = Arrays.asList("peanut", "shellfish", "aznol");
        allergies.addAll(listAllergies);
        medicalRecordToAdd.setAllergies(allergies);

        medicalRecordService.addMedicalRecord(medicalRecordToAdd);

        assertEquals(4, medicalRecords.size());
        assertTrue(databaseService.getMedicalRecords().get(3).getFirstName().equals("Louise"));
        assertTrue(databaseService.getMedicalRecords().get(3).getLastName().equals("Dubois"));
    }

    @Test
    void getMedicalRecordFromFirstAndLastName() {
        when(databaseService.getMedicalRecords()).thenReturn(medicalRecords);
        MedicalRecord medicalRecord1 = medicalRecordService.getMedicalRecordFromFirstAndLastName("Louis", "Boyd");
        assertEquals("03/06/2010", medicalRecord1.getBirthdate());
        assertEquals("Louis", medicalRecord1.getFirstName());
    }

    @Test
    void updateMedicalRecord() {
        when(databaseService.getMedicalRecords()).thenReturn(medicalRecords);
        MedicalRecord medicalRecordToUpdate = medicalRecordService.getMedicalRecordFromFirstAndLastName("Louis", "Boyd");
        medicalRecordToUpdate.setBirthdate("08/12/2005");
        List<String> allergies = new ArrayList<>();
        List<String> listAllergies = Arrays.asList("peanut", "almond", "sugar");
        allergies.addAll(listAllergies);
        medicalRecordToUpdate.setAllergies(allergies);

        medicalRecordService.updateMedicalRecord(medicalRecordToUpdate);

        assertEquals(3, medicalRecords.size());
        assertEquals("08/12/2005", databaseService.getMedicalRecords().get(0).getBirthdate());
        assertEquals(allergies, databaseService.getMedicalRecords().get(0).getAllergies());
    }
}
