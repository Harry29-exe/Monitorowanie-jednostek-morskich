package com.example.monitorowaniejednostekmorskich.ship.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShipLocalization {

    @Id
    @GeneratedValue
    private @NonNull Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private @NonNull Date time;

    @Column(nullable = false)
    private Double xCoordinate;

    @Column(nullable = false)
    private Double yCoordinate;

    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;
}
