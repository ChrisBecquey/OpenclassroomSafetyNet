package com.openclassrooms.safetynet.DTO;

import com.openclassrooms.safetynet.models.Person;

import java.util.List;

public class ChildAlertDTO {
    private String firstName;
    private String lastName;
    private int age;
    private List<Person> personsAtTheAdress;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Person> getPersonsAtTheAdress() {
        return personsAtTheAdress;
    }

    public void setPersonsAtTheAdress(List<Person> personsAtTheAdress) {
        this.personsAtTheAdress = personsAtTheAdress;
    }
}
