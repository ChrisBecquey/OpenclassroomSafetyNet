package com.openclassrooms.safetynet.DTO;

import java.util.List;

public class PersonInfoDTO {
    private String firstName;
    private String lastName;
    private String adress;
    private String email;
    private int age;
    private List medications;
    private List allergies;

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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List getMedications() {
        return medications;
    }

    public void setMedications(List medications) {
        this.medications = medications;
    }

    public List getAllergies() {
        return allergies;
    }

    public void setAllergies(List allergies) {
        this.allergies = allergies;
    }
}
