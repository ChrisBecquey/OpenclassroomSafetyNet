package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.models.Firestation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class FirestationService {

    Logger logger = LoggerFactory.getLogger(FirestationService.class);
    @Autowired
    DatabaseService databaseService;

    public List<Firestation> getFirestationByNumber(int number) {
        return databaseService.getFirestations().stream()
                .filter(station -> station.getStation() == number)
                .collect(Collectors.toList());
    }
    public List<Firestation> getFirestationFromList(List<Integer> stations) {
        return databaseService.getFirestations().stream()
                .filter(station -> stations.contains(station.getStation()))
                .toList();
    }
    public List<Firestation> getFirestationByAdress(String adress) {
        return databaseService.getFirestations().stream()
                .filter(station -> station.getAddress().equals(adress))
                .toList();
    }
    public Firestation getFirestationByNumberAndAdress(int number, String adress) {
        return databaseService.getFirestations()
                .stream()
                .filter(station -> station.getStation() == number && station.getAddress().equals(adress))
                .toList()
                .get(0);
    }
    public List<Firestation> getAllFirestations() {
        return databaseService.getFirestations();
    }

    public Boolean addFirestation(Firestation firestation) {
        databaseService.getFirestations().add(firestation);
        return true;
    }

    public Boolean deleteFirestation(Firestation firestation) {
        if (this.getFirestationByNumberAndAdress(firestation.getStation(), firestation.getAddress()) != null) {
            databaseService.getFirestations().remove(firestation);
            return true;
        }
        return false;
    }

    public Boolean updateFirestation(Firestation firestation) {
        if (this.getFirestationByNumberAndAdress(firestation.getStation(), firestation.getAddress()) != null) {
            databaseService.getFirestations().forEach(f -> {
                if (f.getAddress().equals(firestation.getAddress())){
                    f.setStation(firestation.getStation());
                }
            });
            return true;
        }
        return false;
    }
}
