package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MedicalRecordService {
    @Autowired
    private DatabaseService databaseService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<MedicalRecord> getMedicalRecordsForChild() {
        return databaseService.getMedicalRecords()
                .stream()
                .filter(medicalRecord -> LocalDate.parse(medicalRecord.getBirthdate(), formatter).isAfter(LocalDate.now().minusYears(18)))
                .collect(Collectors.toList());
    }

    public List<MedicalRecord> getMedicalRecordsFromFirstAndLastNameForChild(String firstName, String lastName) {
        return databaseService.getMedicalRecords()
                .stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .filter(medicalRecord -> LocalDate.parse(medicalRecord.getBirthdate(), formatter).isAfter(LocalDate.now().minusYears(18)))
                .collect(Collectors.toList());
    }

    public Boolean addMedicalRecord(MedicalRecord medicalRecord) {
        databaseService.getMedicalRecords().add(medicalRecord);
        return true;
    }

//    public int getAgeFromFirstAndLastName(String firstName, String lastName) {
//        return databaseService.getMedicalRecords()
//                .stream()
//                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
//                .map(m -> Period.between(LocalDate.parse(m.getBirthdate()), LocalDate.now()).getYears())
//                .collect();
//    }
}
