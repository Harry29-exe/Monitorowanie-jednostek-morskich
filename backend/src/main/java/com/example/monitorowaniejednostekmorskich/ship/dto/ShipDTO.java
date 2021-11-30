package com.example.monitorowaniejednostekmorskich.ship.dto;

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

}
