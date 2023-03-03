package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {
    @Autowired
    private DatabaseService databaseService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<MedicalRecord> findAll() {
        return databaseService.getMedicalRecords();
    }
    public List<MedicalRecord> getMedicalRecordsForChild() {
        return databaseService.getMedicalRecords()
                .stream()
                .filter(medicalRecord -> LocalDate.parse(medicalRecord.getBirthdate(), formatter).isAfter(LocalDate.now().minusYears(18)))
                .collect(Collectors.toList());
    }

    public MedicalRecord getMedicalRecordFromFirstAndLastName(String firstName, String lastName) {
        return databaseService.getMedicalRecords()
                .stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .toList()
                .get(0);
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

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        int index = databaseService.getMedicalRecords().indexOf(getMedicalRecordFromFirstAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName()));
        return databaseService.getMedicalRecords().set(index, medicalRecord);
    }

    public Boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        databaseService.getMedicalRecords().remove(medicalRecord);
        return true;
    }
}
