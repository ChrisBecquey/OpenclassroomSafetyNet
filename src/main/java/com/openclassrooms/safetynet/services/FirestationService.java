package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class FirestationService {

    @Autowired
    DatabaseService databaseService;

    public List<Firestation> getFirestationByNumber(int number) {
        return databaseService.getFirestations().stream()
                .filter(station -> station.getStation() == number)
                .collect(Collectors.toList());
    }

    public List<Firestation> getAllFirestations() {
        return databaseService.getFirestations();
    }

    public Boolean addFirestation(Firestation firestation) {
        databaseService.getFirestations().add(firestation);
        return true;
    }
}
