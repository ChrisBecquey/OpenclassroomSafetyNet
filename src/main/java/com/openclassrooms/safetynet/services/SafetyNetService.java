package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.DTO.*;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.MedicalRecord;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.utils.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SafetyNetService {
    @Autowired
    private PersonService personService;
    @Autowired
    private FirestationService firestationService;
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

    PersonFireDTO getPersonFireDTO(String firstName, String lastName) {
        MedicalRecord medicalRecordFromFirstAndLastName = medicalRecordService.getMedicalRecordFromFirstAndLastName(firstName, lastName);
        Person personByFirstAndLastName = personService.getPersonByFirstAndLastName(firstName, lastName);
        ZoneOffset zoneOffset = ZoneId.of("UTC").getRules().getOffset(LocalDateTime.now());

        return new PersonFireDTO(
                firstName,
                lastName,
                personByFirstAndLastName.getPhone(),
                CalculateAge.calculateAge(LocalDate.parse(medicalRecordFromFirstAndLastName.getBirthdate(), CalculateAge.formatter).atStartOfDay().toInstant(zoneOffset)),
                medicalRecordFromFirstAndLastName.getMedications(),
                medicalRecordFromFirstAndLastName.getAllergies());
    }

    public ChildAlertDTO findChildAtTheAdress(String adress) {
        List<Person> personList = personService.getPersonsByAdress(adress);
        ZoneOffset zoneOffset = ZoneId.of("UTC").getRules().getOffset(LocalDateTime.now());


        List<ChildDTO> childDTOS = personList
                .stream()
                .flatMap(person -> medicalRecordService.getMedicalRecordsFromFirstAndLastNameForChild(person.getFirstName(), person.getLastName())
                        .stream()
                        .map(child -> new ChildDTO(child.getFirstName(), child.getLastName(), CalculateAge.calculateAge(
                                LocalDate.parse(child.getBirthdate(), CalculateAge.formatter).atStartOfDay().toInstant(zoneOffset)))))
                .toList();


        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        childAlertDTO.setChildAtTheAdress(childDTOS);
        childAlertDTO.setPersonsAtTheAdress(personList);

        return childAlertDTO;
    }

    public PersonInfoDTO getPersonInfos(String firstName, String lastName) {
        MedicalRecord medicalRecordFromFirstAndLastName = medicalRecordService.getMedicalRecordFromFirstAndLastName(firstName, lastName);
        Person personByFirstAndLastName = personService.getPersonByFirstAndLastName(firstName, lastName);
        ZoneOffset zoneOffset = ZoneId.of("UTC").getRules().getOffset(LocalDateTime.now());

        return new PersonInfoDTO(
                firstName,
                lastName,
                personByFirstAndLastName.getAddress(),
                personByFirstAndLastName.getEmail(),
                CalculateAge.calculateAge(LocalDate.parse(medicalRecordFromFirstAndLastName.getBirthdate(), CalculateAge.formatter).atStartOfDay().toInstant(zoneOffset)),
                medicalRecordFromFirstAndLastName.getMedications(),
                medicalRecordFromFirstAndLastName.getAllergies()
        );
    }
    public List<FloodDTO> getFloodInformations(List<Integer> stations) {
        List<Firestation> firestations = firestationService.getFirestationFromList(stations);

        return firestations
                .stream()
                .flatMap(f -> personService.getPersonsByAdress(f.getAddress())
                        .stream()
                        .map(p -> new FloodDTO(p.getAddress(), List.of(this.getFloodStationDTO(p.getFirstName(), p.getLastName())))))
                        .toList();
    }

    FloodStationDTO getFloodStationDTO(String firstName, String lastName) {
        MedicalRecord medicalRecordFromFirstAndLastName = medicalRecordService.getMedicalRecordFromFirstAndLastName(firstName, lastName);
        Person personByFirstAndLastName = personService.getPersonByFirstAndLastName(firstName, lastName);
        ZoneOffset zoneOffset = ZoneId.of("UTC").getRules().getOffset(LocalDateTime.now());

        return new FloodStationDTO(
                firstName,
                lastName,
                personByFirstAndLastName.getPhone(),
                CalculateAge.calculateAge(LocalDate.parse(medicalRecordFromFirstAndLastName.getBirthdate(), CalculateAge.formatter).atStartOfDay().toInstant(zoneOffset)),
                medicalRecordFromFirstAndLastName.getMedications(),
                medicalRecordFromFirstAndLastName.getAllergies());
    }
}