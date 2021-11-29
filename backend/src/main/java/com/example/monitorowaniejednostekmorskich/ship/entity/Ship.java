package com.example.monitorowaniejednostekmorskich.ship.entity;

import com.example.monitorowaniejednostekmorskich.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ship {

    @Id
    @GeneratedValue
    private @NonNull Long id;

    @Column(nullable = false, updatable = false)
    private Integer mmsi;

    @Column
    private String shipType;

    @Column
    private String name;

    @Column
    private Boolean stillTracked;

    @OneToMany(mappedBy = "ship")
    private Set<ShipLocalization> localizations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracker_id")
    private UserEntity trackedBy;

}
