package com.openclassrooms.safetynet.DTO;

public class PersonDTO {
    private String firstName;
    private String lastName;
    private String adress;
    private String phone;

    public PersonDTO(String firstName, String lastName, String adress, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.phone = phone;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
