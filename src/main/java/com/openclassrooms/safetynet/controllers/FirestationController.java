package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.services.FirestationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {
    @Autowired
    private FirestationService firestationService;
    Logger logger = LoggerFactory.getLogger(FirestationController.class);


    @GetMapping("/firestations")
    public List<Firestation> getAllFirestations() {
        logger.info("Get all fireStation in db");
        return firestationService.getAllFirestations();
    }

    @GetMapping("/firestation")
    public Firestation getFirestationByNumberAndAdress(@RequestParam int number, String adress) {
        logger.info("Get fireStation corresponding to: stationNumber= {} and adress= {}", number, adress);
        return firestationService.getFirestationByNumberAndAdress(number, adress);
    }

    @PostMapping("/firestation")
    public ResponseEntity<String> saveFirestation(@RequestBody Firestation firestation) {
        if (firestationService.addFirestation(firestation)) {
            logger.info("Firestation created");
            return ResponseEntity.status(HttpStatus.CREATED).body("Firestation created");
        } else {
            logger.warn("This fireStation already exist: {}", firestation);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Firestation already exist");
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<String> updateFirestation(@RequestBody Firestation firestation) {
        if (firestationService.updateFirestation(firestation)) {
            logger.info("Firestation updated");
            return ResponseEntity.status(HttpStatus.OK).body("Firestation updated");
        } else {
            logger.warn("This fireStation don't exists: {}", firestation);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The firestation you want to update dont exist");
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<String> deleteFirestation(@RequestParam int number, String adress) {
        if (firestationService.deleteFirestation(firestationService.getFirestationByNumberAndAdress(number, adress))) {
            logger.info("FireStation deleted");
            return ResponseEntity.status(HttpStatus.OK).body("Firestation deleted");
        } else {
            logger.warn("FireStation not found: stationNumber={}, adress={}", number, adress);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No firestation found");
        }
    }
}
