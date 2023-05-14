package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Firestation;
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
class FirestationServiceTest {
    @InjectMocks
    FirestationService firestationService;
    @Mock
    DatabaseService databaseService;

    List<Firestation> firestations = new ArrayList<>();

    @BeforeEach
    void init() {
        Firestation firestation1 = new Firestation();
        firestation1.setAddress("1509 Culver St");
        firestation1.setStation(1);
        firestations.add(firestation1);

        Firestation firestation2 = new Firestation();
        firestation2.setAddress("834 Binoc Ave");
        firestation2.setStation(2);
        firestations.add(firestation2);

        Firestation firestation3 = new Firestation();
        firestation3.setAddress("112 Steppes Pl");
        firestation3.setStation(1);
        firestations.add(firestation3);
    }

    @Test
    void getFirestationByNumber() {
        when(databaseService.getFirestations()).thenReturn(firestations);
        List<Firestation> firestationListByNumber = firestationService.getFirestationByNumber(2);
        assertEquals(1, firestationListByNumber.size());
    }

    @Test
    void getFirestationsByList() {
        when(databaseService.getFirestations()).thenReturn(firestations);
        List<Firestation> firestationListByListOfStations = firestationService.getFirestationFromList(List.of(1,2));
        assertEquals(3, firestationListByListOfStations.size());
    }
    @Test
    void getFirestationByAdress() {
        when(databaseService.getFirestations()).thenReturn(firestations);
        List<Firestation> firestationByAdressList = firestationService.getFirestationByAdress("1509 Culver St");
        assertEquals(1, firestationByAdressList.size());
    }

    @Test
    void getFirestationByNumberAndAdress() {
        when(databaseService.getFirestations()).thenReturn(firestations);
        Firestation firestation1 = firestationService.getFirestationByNumberAndAdress(1, "1509 Culver St");
        assertEquals(1, firestation1.getStation());
        assertEquals("1509 Culver St", firestation1.getAddress());
    }
    @Test
    void getAllFirestations() {
        when(databaseService.getFirestations()).thenReturn(firestations);
        List<Firestation> allFirestations = firestationService.getAllFirestations();
        assertEquals(3, allFirestations.size());
    }

    @Test
    void addFirestation() {
        when(databaseService.getFirestations()).thenReturn(firestations);
        Firestation firestationToAdd = new Firestation();
        firestationToAdd.setAddress("89 Rue du Pont");
        firestationToAdd.setStation(4);

        firestationService.addFirestation(firestationToAdd);
        assertEquals(4, databaseService.getFirestations().size());
        assertTrue(databaseService.getFirestations().get(3).getAddress().equals("89 Rue du Pont"));
    }

    @Test
    void deleteFirestation() {
        when(databaseService.getFirestations()).thenReturn(firestations);
        Firestation firestationToDelete = firestationService.getFirestationByNumberAndAdress(1, "1509 Culver St");
        firestationService.deleteFirestation(firestationToDelete);
        assertEquals(2, databaseService.getFirestations().size());
    }
}