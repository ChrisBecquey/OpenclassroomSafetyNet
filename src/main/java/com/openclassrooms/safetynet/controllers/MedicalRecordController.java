package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.MedicalRecord;
import com.openclassrooms.safetynet.services.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MedicalRecordController {
    @Autowired
    MedicalRecordService medicalRecordService;

    Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @GetMapping("/medicalRecords")
    public List<MedicalRecord> getAllMedicalRecords() {
        logger.info("Get all medical Record in db");
        return medicalRecordService.findAll();
    }

    @GetMapping("/medicalRecord")
    public MedicalRecord getMedicalRecordFromFirstAndLastName(@RequestParam String firstName, String lastName) {
        logger.info("Get medical record corresponding to firstName= {} and lastName= {}", firstName, lastName);
        return medicalRecordService.getMedicalRecordFromFirstAndLastName(firstName, lastName);
    }

    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<String> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        if (medicalRecordService.addMedicalRecord(medicalRecord)) {
            logger.info("MedicalRecord saved");
            return ResponseEntity.status(HttpStatus.CREATED).body("MedicalRecord created");
        } else {
            logger.warn("MedicalRecord already exist: {}", medicalRecord);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("MedicalRecord already exist");
        }
    }

    @PutMapping(value = "/medicalRecord")
    public ResponseEntity<String> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        if (medicalRecordService.updateMedicalRecord(medicalRecord)) {
            logger.info("MedicalRecord updated");
            return ResponseEntity.status(HttpStatus.OK).body("MedicalRecord updated");
        } else {
            logger.warn("This medicalRecord 't exists: {}", medicalRecord);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The medicalRecord you want to update dont exist");
        }
    }

    @DeleteMapping(value = "/medicalRecord")
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, String lastName) {
        if (medicalRecordService.deleteMedicalRecord(medicalRecordService.getMedicalRecordFromFirstAndLastName(firstName, lastName))) {
            logger.info("MedicalRecord deleted");
            return ResponseEntity.status(HttpStatus.OK).body("MedicalRecord deleted");
        } else {
            logger.warn("MedicalRecord not found: firstName={}, lastName={}", firstName, lastName);
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("No medicalRecord found");
        }
    }
}
