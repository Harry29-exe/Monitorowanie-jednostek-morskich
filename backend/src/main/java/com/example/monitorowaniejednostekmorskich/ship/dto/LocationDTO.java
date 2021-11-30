package com.example.monitorowaniejednostekmorskich.ship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class LocationDTO {

    private Double x;
    private Double y;
    private Date time;

}
