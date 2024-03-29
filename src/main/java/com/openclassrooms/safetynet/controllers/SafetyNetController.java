package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.DTO.*;
import com.openclassrooms.safetynet.services.SafetyNetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(SafetyNetController.class);

    @GetMapping(value = "/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommunityEmailDTO> getCommunityEmail(@RequestParam String city) {
        logger.info("Get email of all person living in this city: {}", city);
        return safetyNetService.getEmailFromAllPersonInACity(city);
    }

    @GetMapping(value = "/phoneAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhoneAlertDTO> getPhoneNumber(@RequestParam int firestation) {
        logger.info("Get all phone number of people linked to the firestion: {}", firestation);
        return safetyNetService.getPhoneNumber(firestation);
    }

    @GetMapping(value = "/firestationRange", produces = MediaType.APPLICATION_JSON_VALUE)
    public FirestationDTO getPersonsInRangeOfFirestation(@RequestParam int stationNumber) {
        logger.info("Get all the person in range of this firestation: {}", stationNumber);
        return safetyNetService.findPersonsByStationNumber(stationNumber);
    }

    @GetMapping(value = "/fire", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FireDTO> getPersonsAtTheFireAdress(@RequestParam String address) {
        logger.info("Get all the persons at this address: {}", address);
        return safetyNetService.findPersonsAtTheFireAdress(address);
    }

    @GetMapping(value = "/childAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ChildAlertDTO getChildAtTheAdress(@RequestParam String address) {
        logger.info("Get all the childs at this address: {}", address);
        return safetyNetService.findChildAtTheAdress(address);
    }

    @GetMapping(value = "/personInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonInfoDTO getPersonInfo(@RequestParam String firstName, String lastName) {
        logger.info("Get the infos for this this person: firstName: {}, lastName: {}", firstName, lastName);
        return safetyNetService.getPersonInfos(firstName, lastName);
    }

    @GetMapping(value = "/flood/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FloodDTO> getFloodInformations(@RequestParam List<Integer> stations) {
        logger.info("Get all the persons informations at address of flood for those fireStation: {}", stations);
        return safetyNetService.getFloodInformations(stations);
    }
}