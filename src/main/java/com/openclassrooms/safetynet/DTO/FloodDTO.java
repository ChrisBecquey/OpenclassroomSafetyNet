package com.openclassrooms.safetynet.DTO;

import java.util.List;

public class FloodDTO {
    public FloodDTO(String address, List<FloodStationDTO> floodStationDTO) {
        this.address = address;
        this.floodStationDTOS = floodStationDTO;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FloodStationDTO> getFloodStationDTOS() {
        return floodStationDTOS;
    }

    public void setFloodStationDTOS(List<FloodStationDTO> floodStationDTOS) {
        this.floodStationDTOS = floodStationDTOS;
    }

    private String address;
    private List<FloodStationDTO> floodStationDTOS;
}
