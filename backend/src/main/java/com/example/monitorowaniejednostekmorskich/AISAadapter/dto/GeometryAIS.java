package com.example.monitorowaniejednostekmorskich.AISAadapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeometryAIS {

    private String type;
    private List<Double> coordinates = null;

}
