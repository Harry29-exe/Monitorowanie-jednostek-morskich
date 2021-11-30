package com.example.monitorowaniejednostekmorskich.AISAadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GeometryAIS {

    private String type;
    private List<Double> coordinates = null;

}
