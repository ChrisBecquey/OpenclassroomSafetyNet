package com.openclassrooms.safetynet.DTO;

import java.util.List;

public class PersonFireDTO {
    private String firsName;

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    private String lastName;
    private String phone;

    private int age;

    private List medications;
    private List allergies;

    public PersonFireDTO(String firsName, String lastName, String phone, int age, List medications, List allergies) {
        this.firsName = firsName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }
}


/*
// {
    [{
            stationNUmber:1,
            [
            {
                firstName"firstName", lastName:"lastname", phone:"phone", age, medications:[], allergies:[]
            },
            {
            firstName"firstName", lastName:"lastname", phone:"phone", age, medications:[], allergies:[]
            },
            {
            firstName"firstName", lastName:"lastname", phone:"phone", age, medications:[], allergies:[]
            },
            {
            firstName"firstName", lastName:"lastname", phone:"phone", age, medications:[], allergies:[]
            },
            {
            firstName"firstName", lastName:"lastname", phone:"phone", age, medications:[], allergies:[]
            }
            ]
            }]
// }*/
