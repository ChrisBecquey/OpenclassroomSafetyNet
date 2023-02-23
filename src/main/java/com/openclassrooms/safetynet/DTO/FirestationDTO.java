package com.openclassrooms.safetynet.DTO;

import java.util.List;

public class FirestationDTO {
    private List<PersonDTO> personDTOS;
    private int child;
    private int adult;

    public List<PersonDTO> getPersonDTOS() {
        return personDTOS;
    }

    public void setPersonDTOS(List<PersonDTO> personDTOS) {
        this.personDTOS = personDTOS;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }
}
