package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.models.MedicalRecord;
import com.openclassrooms.safetynet.services.MedicalRecordService;
import com.openclassrooms.safetynet.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MedicalRecordController {
    @Autowired
    MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecords")
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.findAll();
    }

    @GetMapping("/medicalRecord")
    public MedicalRecord getMedicalRecordFromFirstAndLastName(@RequestParam String firstName, String lastName) {
        return medicalRecordService.getMedicalRecordFromFirstAndLastName(firstName, lastName);
    }
    @PostMapping(value = "/medicalRecord")
    public void saveMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.addMedicalRecord(medicalRecord);
    }

    @PutMapping(value = "/medicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
       return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping(value = "/medicalRecord")
    public  Boolean deleteMedicalRecord(@RequestParam String firstName, String lastName) {
        return medicalRecordService.deleteMedicalRecord(medicalRecordService.getMedicalRecordFromFirstAndLastName(firstName, lastName));
    }
}
