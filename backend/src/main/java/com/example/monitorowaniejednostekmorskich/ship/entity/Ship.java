package com.example.monitorowaniejednostekmorskich.ship.entity;

import com.example.monitorowaniejednostekmorskich.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ship {

    @Id
    @GeneratedValue
    private @NonNull
    Long id;

    @Column(unique = true, nullable = false)
    private @NonNull
    UUID publicId = UUID.randomUUID();

    @Column(nullable = false, updatable = false)
    private @NonNull
    Integer mmsi;

    @Column
    private @Nullable
    String shipType;

    @Column
    private @Nullable
    String name;

    @Column
    private @NonNull
    Boolean stillTracked;

    @OneToMany(mappedBy = "ship")
    private Set<ShipLocalization> localizations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracker_id")
    private UserEntity trackedBy;

    public Ship(@NonNull Integer mmsi,
                @Nullable String shipType,
                @Nullable String name,
                UserEntity trackedBy) {
        this.mmsi = mmsi;
        this.shipType = shipType;
        this.name = name;
        this.trackedBy = trackedBy;
        stillTracked = true;
    }

    public Ship(@NonNull Integer mmsi, UserEntity trackedBy) {
        this.mmsi = mmsi;
        this.trackedBy = trackedBy;
    }
}
