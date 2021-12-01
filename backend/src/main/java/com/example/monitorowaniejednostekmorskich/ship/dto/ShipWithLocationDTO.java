package com.example.monitorowaniejednostekmorskich.ship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ShipWithLocationDTO {

    private @NotNull ShipDTO shipDTO;
    private @Nullable
    LocationDTO lastLocation;

}

