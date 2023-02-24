package com.openclassrooms.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.services.MedicalRecordService;
import com.openclassrooms.safetynet.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MedicalRecordController {
    MedicalRecordService medicalRecordService;

    @Autowired
    private PersonService personService;

    ObjectMapper mapper = new ObjectMapper();

}
//    @GetMapping("childAlert/{adress}")
//    public List<String> getChildListAtThisAdress(@PathVariable String adress) {
//        //On récupère la liste de personnes à cette adresse
//        //On récupère la liste de medicalRecord uniquement pour les enfants
//        return personService.getPersonsByAdress(adress)
//                .stream()
//                .map(person -> medicalRecordService.getMedicalRecordsFromFirstAndLastNameForChild(person.getFirstName(), person.getLastName()))
//                .collect(Collectors.toList());
//    }
