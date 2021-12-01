package com.example.monitorowaniejednostekmorskich.ship.dto;

import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipDTO {

    private UUID publicId;
    private Integer mmsi;
    private String shipType;
    private String name;
    private Boolean stillTracked;

    public static ShipDTO from(Ship ship) {
        return new ShipDTO(
                ship.getPublicId(),
                ship.getMmsi(),
                ship.getShipType(),
                ship.getName(),
                ship.getStillTracked()
        );
    }

}
