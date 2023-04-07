package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.DTO.CommunityEmailDTO;
import com.openclassrooms.safetynet.DTO.FirestationDTO;
import com.openclassrooms.safetynet.DTO.PersonDTO;
import com.openclassrooms.safetynet.DTO.PhoneAlertDTO;
import com.openclassrooms.safetynet.services.SafetyNetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SafetyNetController.class)
class SafetyNetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SafetyNetService safetyNetService;

    @Test
    void getCommunityEmail() throws Exception {
        String city = "Lille";
        List<CommunityEmailDTO> communityEmailDTOList = new ArrayList<>();
        CommunityEmailDTO communityEmailDTO1 = new CommunityEmailDTO("jaboyd@email.com");
        communityEmailDTO1.setEmail("jaboyd@email.com");
        communityEmailDTOList.add(communityEmailDTO1);
        CommunityEmailDTO communityEmailDTO2 = new CommunityEmailDTO("drk@email.com");
        communityEmailDTO2.setEmail("drk@email.com");
        communityEmailDTOList.add(communityEmailDTO2);

        when(safetyNetService.getEmailFromAllPersonInACity(city)).thenReturn(communityEmailDTOList);

        mockMvc.perform(get("/communityEmail")
                        .param("city", city)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value("jaboyd@email.com"));

    }

    @Test
    void getPhoneNumber() throws Exception {
        int firestation = 1;
        List<PhoneAlertDTO> phoneAlertDTOList = new ArrayList<>();
        PhoneAlertDTO phoneAlertDTO1 = new PhoneAlertDTO("841-874-6512");
        phoneAlertDTO1.setPhone("841-874-6512");
        phoneAlertDTOList.add(phoneAlertDTO1);
        PhoneAlertDTO phoneAlertDTO2 = new PhoneAlertDTO("841-874-6513");
        phoneAlertDTO2.setPhone("841-874-6513");
        phoneAlertDTOList.add(phoneAlertDTO2);

        when(safetyNetService.getPhoneNumber(firestation)).thenReturn(phoneAlertDTOList);

        mockMvc.perform(get("/phoneAlert")
                .param("firestation", Integer.toString(firestation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].phone").value("841-874-6512"));
    }

    @Test
    void getPersonsInRangeOfFirestation() throws Exception {
        int firestation = 1;
        FirestationDTO firestationDTO = new FirestationDTO();
        List<PersonDTO> personDTOList = new ArrayList<>();
        PersonDTO personDTO1 = new PersonDTO("Louis", "Boyd", "12 rue du pré", "666-777-888");
        personDTO1.setFirstName("Louis");
        personDTO1.setLastName("Boyd");
        personDTO1.setAdress("12 rue du pré");
        personDTO1.setPhone("666-777-888");
        personDTOList.add(personDTO1);

        PersonDTO personDTO2 = new PersonDTO("Jeanne", "Dupont", "15 rue de la croix", "888-999-777");
        personDTO2.setFirstName("Jeanne");
        personDTO2.setLastName("Dupont");
        personDTO2.setAdress("15 rue de la croix");
        personDTO2.setPhone("888-999-777");
        personDTOList.add(personDTO2);

        firestationDTO.setPersonDTOS(personDTOList);
        firestationDTO.setAdult(1);
        firestationDTO.setChild(1);

        when(safetyNetService.findPersonsByStationNumber(firestation)).thenReturn(firestationDTO);

        mockMvc.perform(get("/firestationRange")
                .param("firestation", Integer.toString(firestation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}