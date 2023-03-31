package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.DTO.CommunityEmailDTO;
import com.openclassrooms.safetynet.DTO.FirestationDTO;
import com.openclassrooms.safetynet.DTO.PersonDTO;
import com.openclassrooms.safetynet.DTO.PhoneAlertDTO;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.MedicalRecord;
import com.openclassrooms.safetynet.models.Person;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class SafetyNetServiceTest {
    @InjectMocks
    SafetyNetService safetyNetService;

    @Mock
    PersonService personService;

    @Mock
    FirestationService firestationService;
    @Mock
    MedicalRecordService medicalRecordService;

    List<Person> persons = new ArrayList<>();
    List<Firestation> firestations = new ArrayList<>();
    List<MedicalRecord> medicalRecords = new ArrayList<>();


    @Test
    void getEmailFromAllPersonInACity() {
        Person person1 = new Person();
        person1.setFirstName("Albert");
        person1.setLastName("Lambert");
        person1.setAddress("12 rue du fou");
        person1.setEmail("Albert.dupont@gmail.com");
        person1.setPhone("777-555-1122");
        person1.setZip("31000");
        person1.setCity("Toulouse");
        persons.add(person1);

        Person person2 = new Person();
        person2.setFirstName("Jeanne");
        person2.setLastName("Lambert");
        person2.setAddress("12 rue du fou");
        person2.setEmail("Jeanne.lambert@gmail.com");
        person2.setPhone("999-555-1133");
        person2.setZip("31000");
        person2.setCity("Toulouse");
        persons.add(person2);

        when(personService.getPersonsByCity(any())).thenReturn(persons);
        List<CommunityEmailDTO> emailList = safetyNetService.getEmailFromAllPersonInACity("Toulouse");
        assertEquals(2, emailList.size());
        assertTrue(emailList.get(0).getEmail().equals("Albert.dupont@gmail.com"));

    }

    @Test
    void getPhoneNumber() {
        Firestation firestation1 = new Firestation();
        firestation1.setAddress("12 rue du fou");
        firestation1.setStation(1);
        firestations.add(firestation1);

        Person person1 = new Person();
        person1.setFirstName("Albert");
        person1.setLastName("Lambert");
        person1.setAddress("12 rue du fou");
        person1.setEmail("Albert.dupont@gmail.com");
        person1.setPhone("777-555-1122");
        person1.setZip("31000");
        person1.setCity("Toulouse");
        persons.add(person1);

        Person person2 = new Person();
        person2.setFirstName("Jeanne");
        person2.setLastName("Lambert");
        person2.setAddress("12 rue du fou");
        person2.setEmail("Jeanne.lambert@gmail.com");
        person2.setPhone("999-555-1133");
        person2.setZip("31000");
        person2.setCity("Toulouse");
        persons.add(person2);

        when(firestationService.getFirestationByNumber(anyInt())).thenReturn(firestations);
        when(personService.getPersonsByAdress(any())).thenReturn(persons);

        List<PhoneAlertDTO> phoneNumberList = safetyNetService.getPhoneNumber(1);
        assertEquals(2, phoneNumberList.size());
        assertTrue(phoneNumberList.get(0).getPhone().equals("777-555-1122"));

    }

    @Test
    void getPersonsInRangeOfFirestation() {
        Firestation firestation = new Firestation();
        firestation.setAddress("12 rue du pont");
        firestation.setStation(1);
        firestations.add(firestation);

        Person person1 = new Person();
        person1.setFirstName("Albert");
        person1.setLastName("Lambert");
        person1.setAddress("12 rue du pont");
        person1.setEmail("Albert.dupont@gmail.com");
        person1.setPhone("777-555-1122");
        person1.setZip("31000");
        person1.setCity("Toulouse");
        persons.add(person1);

        Person person2 = new Person();
        person2.setFirstName("Jeanne");
        person2.setLastName("Lambert");
        person2.setAddress("12 rue du pont");
        person2.setEmail("Jeanne.lambert@gmail.com");
        person2.setPhone("999-555-1133");
        person2.setZip("31000");
        person2.setCity("Toulouse");
        persons.add(person2);

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Albert");
        medicalRecord1.setLastName("Lambert");
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

        when(personService.getPersonsByAdress(any())).thenReturn(persons);
        when(firestationService.getFirestationByNumber(anyInt())).thenReturn(firestations);
        when(medicalRecordService.getMedicalRecordsFromFirstAndLastNameForChild(any(), any())).thenReturn(medicalRecords);

        FirestationDTO firestationDTO = safetyNetService.findPersonsByStationNumber(1);
        assertEquals(firestationDTO.getPersonDTOS().size(), 2);
        assertEquals(firestationDTO.getChild(), 1);
        assertEquals(firestationDTO.getAdult(), 1);
    }

}
