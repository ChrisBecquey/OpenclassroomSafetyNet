package com.openclassrooms.safetynet.models;

import java.util.ArrayList;

public class Database {
    private ArrayList<Person> persons;
    private ArrayList<Firestation> firestations;
    private ArrayList<MedicalRecord> medicalrecords;

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Firestation> getFirestations() {
        return firestations;
    }

    public void setFirestations(ArrayList<Firestation> firestations) {
        this.firestations = firestations;
    }

    public ArrayList<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(ArrayList<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }
}
