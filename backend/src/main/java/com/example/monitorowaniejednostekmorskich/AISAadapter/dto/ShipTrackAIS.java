package com.example.monitorowaniejednostekmorskich.AISAadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class ShipTrackAIS {
    private String timeStamp;
    private Double sog;
    private Double rot;
    private Integer navstat;
    private Integer mmsi;
    private Double cog;
    private GeometryAIS geometry;
    private Integer shipType;
    private String name;
    private Integer imo;
    private String callsign;
    private String country;
    private String eta;
    private String destination;
    private Boolean isSurvey;
    private Integer heading;
    private Double draught;
    private Integer a;
    private Integer b;
    private Integer c;
    private Integer d;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
