package com.example.monitorowaniejednostekmorskich.ship.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ship_localization")
@Getter
@Setter
@NoArgsConstructor
public class ShipLocalization {

    @Id
    @GeneratedValue
    private @NonNull
    Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private @NonNull
    Date time;

    @Column(nullable = false, name = "x_coordinate")
    private Double xCoordinate;

    @Column(nullable = false, name = "y_coordinate")
    private Double yCoordinate;

    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;

    public ShipLocalization(@NonNull Date time, Double xCoordinate, Double yCoordinate, Ship ship) {
        this.time = time;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.ship = ship;
    }
}
