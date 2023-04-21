package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.DTO.CommunityEmailDTO;
import com.openclassrooms.safetynet.DTO.FireDTO;
import com.openclassrooms.safetynet.DTO.FirestationDTO;
import com.openclassrooms.safetynet.DTO.PhoneAlertDTO;
import com.openclassrooms.safetynet.services.SafetyNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SafetyNetController {
    @Autowired
    SafetyNetService safetyNetService;
    @GetMapping(value = "/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommunityEmailDTO> getCommunityEmail(@RequestParam String city) {
        return safetyNetService.getEmailFromAllPersonInACity(city);
    }

    @GetMapping(value = "/phoneAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhoneAlertDTO> getPhoneNumber(@RequestParam int firestation) {
        return safetyNetService.getPhoneNumber(firestation);
    }

    @GetMapping(value = "/firestationRange", produces = MediaType.APPLICATION_JSON_VALUE)
    public FirestationDTO getPersonsInRangeOfFirestation(@RequestParam int firestation) {
        return  safetyNetService.findPersonsByStationNumber(firestation);
    }

    @GetMapping(value = "/fire", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FireDTO> getPersonsAtTheFireAdress(@RequestParam String adress) {
        return safetyNetService.findPersonsAtTheFireAdress(adress);
    }


     // childALert => liste d'enfant habitant à l'adresse (nom + prénom age + list autres membre du foyer
    // dans le cas ou pas d'enfants, liste vide

    //floodStations => Liste de tous les foyers desservis par la caserne
    // personnes par adresse, nom + prénom + phone + age + antécédents médicaux

    // personInfo => nom + adresse + age + mail + antécédents Médicaux de chaque habitant, si plusieurs fois le même nom elle doivent apparaitre
}
