package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.FirestationService;
import com.openclassrooms.safetynet.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FirestationController {
    @Autowired
    private FirestationService firestationService;

    @GetMapping("/firestations")
    public List<Firestation> getAllFirestations() {
        return firestationService.getAllFirestations();
    }

    @GetMapping("/firestation")
    public Firestation getFirestationByNumberAndAdress(@RequestParam int number, String adress) {
        return firestationService.getFirestationByNumberAndAdress(number, adress);
    }
    @PostMapping("/firestation")
    public ResponseEntity<String> saveFirestation(@RequestBody Firestation firestation) {
        if(firestationService.addFirestation(firestation))
            return ResponseEntity.status(HttpStatus.CREATED).body("Firestation created");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Firestation already exist");
    }

    @PutMapping("/firestation")
    public ResponseEntity<String> updateFirestation(@RequestBody Firestation firestation) {
        if(firestationService.updateFirestation(firestation))
            return ResponseEntity.status(HttpStatus.OK).body("Firestation updated");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The firestation you want to update dont exist");
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<String> deleteFirestation(@RequestParam int number, String adress) {
        if (firestationService.deleteFirestation(firestationService.getFirestationByNumberAndAdress(number, adress)))
            return ResponseEntity.status(HttpStatus.OK).body("Firestation deleted");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No firestation found");
    }

}
