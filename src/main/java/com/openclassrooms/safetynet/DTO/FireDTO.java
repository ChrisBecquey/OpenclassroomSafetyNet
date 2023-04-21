package com.openclassrooms.safetynet.DTO;

import java.util.List;

public class FireDTO {
    private int stationNumber;
    private PersonFireDTO personFireDTO;

    public FireDTO(int stationNumber, PersonFireDTO personFireDTO) {
        this.stationNumber = stationNumber;
        this.personFireDTO = personFireDTO;
    }


    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public PersonFireDTO getPersonFireDTO() {
        return personFireDTO;
    }

    public void setPersonFireDTO(PersonFireDTO personFireDTO) {
        this.personFireDTO = personFireDTO;
    }
}
