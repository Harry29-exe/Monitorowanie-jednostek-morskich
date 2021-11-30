package com.example.monitorowaniejednostekmorskich.ship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ShipWithLocationDTO {

    private LocationDTO lastLocation;

}

