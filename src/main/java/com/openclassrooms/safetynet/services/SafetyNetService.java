package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.DTO.*;
import com.openclassrooms.safetynet.models.Firestation;
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

//    public List<Person> getPersonsInFirestationRange(int station) {
//        List<Firestation> firestations = firestationService.getFirestationByNumber(station);
//        return firestations.
//                stream()
//                .flatMap(f -> personService.getPersonsByAdress(f.getAddress())
//                        .stream()
//                        .map(p ->
//                                        new FirestationDTO(new PersonDTO(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone()), ),
//                                ))
//    }


}


//    public int getAge() {
//        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsForChild();
//        LocalDate today = LocalDate.now();
//
//
//        return medicalRecords
//                .stream()
//                .map(m -> Period.between(LocalDate.parse(m.getBirthdate()), LocalDate.now()).getYears())
//                .collect();
//    }
//    public List<ChildAlertDTO> getChildInformationAtTheAdress(String adress) {
//        List<Person> persons = personService.getPersonsByAdress(adress);
//        return persons
//                .stream()
//                .flatMap(p -> medicalRecordService.getMedicalRecordsFromFirstAndLastNameForChild(p.getFirstName(), p.getLastName())
//                        .stream()
//                        .map(m -> new ChildAlertDTO(m.getFirstName(), m.getLastName(), Period.between(LocalDate.parse(m.getBirthdate(), LocalDate.now()).getYear()))))
//    }