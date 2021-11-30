package com.example.monitorowaniejednostekmorskich.ship.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LocationDTO {

    private Double x;
    private Double y;
    private Date time;

}
