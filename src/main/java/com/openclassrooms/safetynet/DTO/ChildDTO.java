package com.openclassrooms.safetynet.DTO;

public class ChildDTO {
    private String firstName;

    public ChildDTO(String firstName, String lastName, Integer calculateAge) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = calculateAge;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String lastName;
    private int age;
}
