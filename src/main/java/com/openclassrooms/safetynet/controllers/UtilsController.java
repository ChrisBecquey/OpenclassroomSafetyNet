package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.DTO.CommunityEmailDTO;
import com.openclassrooms.safetynet.DTO.PhoneAlertDTO;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UtilsController {
    @Autowired
    UtilsService utilsService;

    @GetMapping(value = "/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommunityEmailDTO> getCommunityEmail(@RequestParam String city) {
        return utilsService.getEmailFromAllPersonInACity(city);
    }

    @GetMapping(value = "/phoneAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhoneAlertDTO> getPhoneNumber(@RequestParam int firestation) {
        return utilsService.getPhoneNumber(firestation);
    }


}
