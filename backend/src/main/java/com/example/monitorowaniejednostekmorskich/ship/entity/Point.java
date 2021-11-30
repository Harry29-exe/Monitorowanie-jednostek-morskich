package com.example.monitorowaniejednostekmorskich.ship.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    private Double x;
    private Double y;

}
