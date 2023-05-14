package com.openclassrooms.safetynet.controllers;

import com.openclassrooms.safetynet.DTO.*;
import com.openclassrooms.safetynet.models.Person;
import com.openclassrooms.safetynet.services.SafetyNetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
                        .param("stationNumber", Integer.toString(firestation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getPersonsAtTheFireAdress() throws Exception {
        String address = "12 rue du pont";
        List<FireDTO> fireDTOList = new ArrayList<>();
        PersonFireDTO personFireDTO1 = new PersonFireDTO();
        personFireDTO1.setFirsName("Roger");
        personFireDTO1.setLastName("Boyd");
        personFireDTO1.setPhone("777-888-999");
        personFireDTO1.setAge(35);
        personFireDTO1.setMedications(List.of("aznol:350mg", "hydrapermazol:100mg"));
        personFireDTO1.setAllergies(List.of("peanut"));

        PersonFireDTO personFireDTO2 = new PersonFireDTO();
        personFireDTO2.setFirsName("Felicia");
        personFireDTO2.setLastName("Dupont");
        personFireDTO2.setPhone("888-999-777");
        personFireDTO2.setAge(22);
        personFireDTO2.setMedications(List.of());
        personFireDTO2.setAllergies(List.of("shellfish"));

        FireDTO fireDTO1 = new FireDTO(1, personFireDTO1);
        fireDTO1.setStationNumber(1);
        fireDTO1.setPersonFireDTO(personFireDTO1);
        fireDTOList.add(fireDTO1);

        FireDTO fireDTO2 = new FireDTO(1, personFireDTO2);
        fireDTO2.setStationNumber(1);
        fireDTO2.setPersonFireDTO(personFireDTO2);

        when(safetyNetService.findPersonsAtTheFireAdress(address)).thenReturn(fireDTOList);

        mockMvc.perform(get("/fire")
                        .param("address", address)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getChildAtTheAdress() throws Exception {
        String address = "12 rue du pont";
        ChildAlertDTO childAlertDTO = new ChildAlertDTO();

        List<Person> persons = new ArrayList<>();
        List<ChildDTO> childDTOS = new ArrayList<>();
        Person person1 = new Person();
        person1.setFirstName("Albert");
        person1.setLastName("Lambert");
        person1.setAddress("12 rue du pont");
        person1.setEmail("Albert.dupont@gmail.com");
        person1.setPhone("777-555-1122");
        person1.setZip("31000");
        person1.setCity("Toulouse");
        persons.add(person1);

        Person person2 = new Person();
        person2.setFirstName("Jeanne");
        person2.setLastName("Lambert");
        person2.setAddress("12 rue du pont");
        person2.setEmail("Jeanne.lambert@gmail.com");
        person2.setPhone("999-555-1133");
        person2.setZip("31000");
        person2.setCity("Toulouse");
        persons.add(person2);

        ChildDTO childDTO1 = new ChildDTO("Jeanne", "Lambert", 12);
        childDTO1.setFirstName("Jeanne");
        childDTO1.setLastName("Lambert");
        childDTO1.setAge(12);
        childDTOS.add(childDTO1);

        childAlertDTO.setChildAtTheAdress(childDTOS);
        childAlertDTO.setPersonsAtTheAdress(persons);

        when(safetyNetService.findChildAtTheAdress(address)).thenReturn(childAlertDTO);

        mockMvc.perform(get("/childAlert")
                        .param("address", "12 rue du pont")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getPersonInfo() throws Exception {
        PersonInfoDTO personInfoDTO = new PersonInfoDTO("Jeanne", "Lambert", "12 rue du pont", "jeanne.lambert@gmail.com", 25, List.of("insuline"), List.of("peanut"));
        personInfoDTO.setFirstName("Jeanne");
        personInfoDTO.setLastName("Lambert");
        personInfoDTO.setAdress("12 rue du pont");
        personInfoDTO.setEmail("jeanne.lambert@gmail.com");
        personInfoDTO.setAge(25);
        personInfoDTO.setMedications(List.of("insuline"));
        personInfoDTO.setAllergies(List.of("peanut"));
        when(safetyNetService.getPersonInfos(any(), any())).thenReturn(personInfoDTO);

        mockMvc.perform(get("/personInfo")
                        .param("firstName", "Jeanne", "lastName", "Lambert")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getFloodInformation() throws Exception {
        FloodStationDTO floodStationDTO1 = new FloodStationDTO("Jean", "Dupont", "666-777-888", 25, List.of("insuline"), List.of("peanuts"));
        FloodStationDTO floodStationDTO2 = new FloodStationDTO("Pauline", "Dupont", "444-555-666", 29, List.of("doliprane"), List.of("butter"));
        FloodStationDTO floodStationDTO3 = new FloodStationDTO("Robert", "Kert", "777-888-999", 85, List.of("doliprane", "insuline"), List.of("eggs"));

        FloodDTO floodDTO1 = new FloodDTO("12 rue du pont", List.of(floodStationDTO1, floodStationDTO2));
        FloodDTO floodDTO2 = new FloodDTO("3 rue de la croix", List.of(floodStationDTO3));

        List<FloodDTO> floodDTOList = new ArrayList<>();
        floodDTOList.add(floodDTO1);
        floodDTOList.add(floodDTO2);
        when(safetyNetService.getFloodInformations(any())).thenReturn(floodDTOList);

        mockMvc.perform(get("/flood/stations")
                .param("stations", "1, 2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}