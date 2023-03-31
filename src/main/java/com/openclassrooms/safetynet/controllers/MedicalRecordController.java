package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.models.MedicalRecord;
import com.openclassrooms.safetynet.services.MedicalRecordService;
import com.openclassrooms.safetynet.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        if(medicalRecordService.addMedicalRecord(medicalRecord))
            return ResponseEntity.status(HttpStatus.CREATED).body("MedicalRecord created");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("MedicalRecord already exist");
    }

    @PutMapping(value = "/medicalRecord")
    public ResponseEntity<String> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        if(medicalRecordService.updateMedicalRecord(medicalRecord))
            return  ResponseEntity.status(HttpStatus.OK).body("MedicalRecord updated");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The medicalRecord you want to update dont exist");
    }

    @DeleteMapping(value = "/medicalRecord")
    public  ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, String lastName) {
        if(medicalRecordService.deleteMedicalRecord(medicalRecordService.getMedicalRecordFromFirstAndLastName(firstName, lastName)))
            return ResponseEntity.status(HttpStatus.OK).body("MedicalRecord deleted");
        return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("No medicalRecord found");
    }
}
