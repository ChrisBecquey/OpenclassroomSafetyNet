package com.openclassrooms.safetynet.DTO;

import java.util.List;

public class FireDTO {
    private int stationNumber;
    private String firstName;
    private String LastName;
    private int age;
    private String phone;
    private List medications;
    private List allergies;

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
