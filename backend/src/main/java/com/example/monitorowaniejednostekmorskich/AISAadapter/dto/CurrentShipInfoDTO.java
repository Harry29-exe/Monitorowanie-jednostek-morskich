package com.example.monitorowaniejednostekmorskich.AISAadapter.dto;

import com.example.monitorowaniejednostekmorskich.ship.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentShipInfoDTO {

    private Integer mmsi;
    private String name;
    private String type;
    private String destination;
    private LocationDTO currentLocation;

}
