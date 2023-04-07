package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.DTO.*;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.MedicalRecord;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.utils.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    @Autowired
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

        List<MedicalRecord> childs = persons
                .stream()
                .flatMap(p -> medicalRecordService.getMedicalRecordsFromFirstAndLastNameForChild(p.getFirstName(), p.getLastName())
                        .stream())
                .distinct()
                .toList();

        int child = childs.size();
        int adult = persons.size() - child;

        firestationDTO.setPersonDTOS(persons);
        firestationDTO.setAdult(adult);
        firestationDTO.setChild(child);
        return firestationDTO;
    }

    public List<FireDTO> findPersonsAtTheFireAdress(String adress) {
        List<Firestation> firestationList = firestationService.getFirestationByAdress(adress);

        return firestationList
                        .stream()
                                .flatMap(f -> personService.getPersonsByAdress(f.getAddress())
                                        .stream()
                                        .map(p -> new FireDTO(f.getStation(), this.getPersonFireDTO(p.getFirstName(), p.getLastName()))))
                        .toList();
    }

    private PersonFireDTO getPersonFireDTO(String firstName, String lastName) {
        MedicalRecord medicalRecordFromFirstAndLastName = medicalRecordService.getMedicalRecordFromFirstAndLastName(firstName, lastName);
        Person personByFirstAndLastName = personService.getPersonByFirstAndLastName(firstName, lastName);

        return new PersonFireDTO(firstName, lastName, personByFirstAndLastName.getPhone(), CalculateAge.calculateAge(Instant.parse(medicalRecordFromFirstAndLastName.getBirthdate())), medicalRecordFromFirstAndLastName.getMedications(), medicalRecordFromFirstAndLastName.getAllergies());
    }

}