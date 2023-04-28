package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Database;
import com.openclassrooms.safetynet.models.Firestation;
import com.openclassrooms.safetynet.models.MedicalRecord;
import com.openclassrooms.safetynet.models.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {
    private Database database;

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public List<Person> getPersons() {
        return database.getPersons();
    }

    public List<Firestation> getFirestations() {
        return database.getFirestations();
    }

    public List<MedicalRecord> getMedicalRecords() {
        return database.getMedicalrecords();
    }


}
