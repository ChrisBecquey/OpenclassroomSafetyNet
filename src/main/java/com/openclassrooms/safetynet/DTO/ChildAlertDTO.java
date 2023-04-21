package com.openclassrooms.safetynet.DTO;

import com.openclassrooms.safetynet.models.Person;

import java.util.List;

public class ChildAlertDTO {
    private List<ChildDTO> childAtTheAdress;
    private List<Person> personsAtTheAdress;


    public List<ChildDTO> getChildAtTheAdress() {
        return childAtTheAdress;
    }

    public void setChildAtTheAdress(List<ChildDTO> childAtTheAdress) {
        this.childAtTheAdress = childAtTheAdress;
    }

    public List<Person> getPersonsAtTheAdress() {
        return personsAtTheAdress;
    }

    public void setPersonsAtTheAdress(List<Person> personsAtTheAdress) {
        this.personsAtTheAdress = personsAtTheAdress;
    }

}
