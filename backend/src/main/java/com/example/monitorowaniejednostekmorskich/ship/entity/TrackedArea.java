package com.example.monitorowaniejednostekmorskich.ship.entity;

import com.example.monitorowaniejednostekmorskich.user.entity.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity
public class TrackedArea {

    @Id
    @GeneratedValue
    private @NotNull Long id;

    @Column(unique = true, updatable = false)
    @GeneratedValue
    private @NotNull UUID publicId;

    private Double fromX;
    private Double fromY;
    private Double toX;
    private Double toY;

    @ManyToOne
    @JoinColumn(name = "monitored_by", nullable = false)
    private @NotNull UserEntity user;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Ship> shipsInArea;

}
