package com.example.monitorowaniejednostekmorskich.user.entity;

import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_entity")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private @NonNull
    Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private @NonNull
    String username;

    @Column(nullable = false)
    private @NonNull
    String password;

    @OneToMany(mappedBy = "trackedBy")
    private Set<Ship> trackedShips;

    public UserEntity(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }
}
