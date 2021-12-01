package com.example.monitorowaniejednostekmorskich.ship.dto;

import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class LocationDTO {

    private Double x;
    private Double y;
    private Date time;

    public static LocationDTO from(ShipLocalization localization) {
        return new LocationDTO(localization.getXCoordinate(),
                localization.getYCoordinate(),
                localization.getTime());
    }

}
