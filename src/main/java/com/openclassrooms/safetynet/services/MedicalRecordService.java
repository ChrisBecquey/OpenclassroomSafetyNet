package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.MedicalRecord;
import com.openclassrooms.safetynet.utils.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {
    @Autowired
    private DatabaseService databaseService;

    public List<MedicalRecord> findAll() {
        return databaseService.getMedicalRecords();
    }

    public List<MedicalRecord> getMedicalRecordsForChild() {
        return databaseService.getMedicalRecords()
                .stream()
                .filter(medicalRecord -> LocalDate.parse(medicalRecord.getBirthdate(), CalculateAge.formatter).isAfter(LocalDate.now().minusYears(18)))
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
                .filter(medicalRecord -> LocalDate.parse(medicalRecord.getBirthdate(), CalculateAge.formatter).isAfter(LocalDate.now().minusYears(18)))
                .collect(Collectors.toList());
    }

    public Boolean addMedicalRecord(MedicalRecord medicalRecord) {
        databaseService.getMedicalRecords().add(medicalRecord);
        return true;
    }

    public Boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        if (this.getMedicalRecordFromFirstAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName()) != null) {
            int index = databaseService.getMedicalRecords().indexOf(getMedicalRecordFromFirstAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName()));
            databaseService.getMedicalRecords().set(index, medicalRecord);
            return true;
        }
        return false;
    }

    public Boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        if (this.getMedicalRecordFromFirstAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName()) != null) {
            databaseService.getMedicalRecords().remove(medicalRecord);
            return true;
        }
        return false;
    }
}
