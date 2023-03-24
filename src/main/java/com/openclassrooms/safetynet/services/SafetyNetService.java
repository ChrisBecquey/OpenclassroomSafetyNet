package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.DTO.*;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SafetyNetService {
    @Autowired
    private PersonService personService;
    @Autowired
    private FirestationService firestationService;

    @Autowired
    private DatabaseService databaseService;

    private MedicalRecordService medicalRecordService;

    public List<CommunityEmailDTO> getEmailFromAllPersonInACity(String city) {
        return personService.getPersonsByCity(city)
                .stream()
                .map(p -> new CommunityEmailDTO(p.getEmail()))
                .collect(Collectors.toList());
    }

    public List<PhoneAlertDTO> getPhoneNumber(int firestation) {
        List<Firestation> firestations = firestationService.getFirestationByNumber(firestation);
        return firestations
                .stream()
                .flatMap(f -> personService.getPersonsByAdress(f.getAddress())
                        .stream()
                        .map(p -> new PhoneAlertDTO(p.getPhone()))
                        .distinct())
                .collect(Collectors.toList());
    }


    public FirestationDTO findPersonsByStationNumber(int stationNumber) {
        FirestationDTO firestationDTO = new FirestationDTO();
        List<Firestation> firestations = firestationService.getFirestationByNumber(stationNumber);
        List<PersonDTO> persons = firestations
                .stream()
                .flatMap(f -> personService.getPersonsByAdress(f.getAddress())
                        .stream()
                        .map(p -> new PersonDTO(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone()))
                        .distinct())
                .toList();

        List<MedicalRecord> medicals = persons
                .stream()
                .map(p -> medicalRecordService.getMedicalRecordFromFirstAndLastName(p.getFirstName(), p.getLastName()))
                .toList();

        List<MedicalRecord> childs = medicals
                .stream()
                .flatMap(p -> medicalRecordService.getMedicalRecordsForChild()
                        .stream()
                        .distinct())
                .toList();

        int child = childs.size();
        int adult = persons.size() - child;

        firestationDTO.setPersonDTOS(persons);
        firestationDTO.setAdult(adult);
        firestationDTO.setChild(child);
        return firestationDTO;
    }
}