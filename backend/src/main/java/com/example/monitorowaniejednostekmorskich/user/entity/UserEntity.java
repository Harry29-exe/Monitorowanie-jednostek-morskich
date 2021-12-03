package com.example.monitorowaniejednostekmorskich.user.entity;

import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_entity")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private
//    @NonNull
    Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String username;
//    @NonNull


    @Column(nullable = false)
    private String password;
//    @NonNull


    @OneToMany(mappedBy = "trackedBy")
    private Set<Ship> trackedShips = new HashSet<>();

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserEntity(Long id, String username, String password, Set<Ship> trackedShips) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.trackedShips = trackedShips;
    }

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Ship> getTrackedShips() {
        return trackedShips;
    }

    public void setTrackedShips(Set<Ship> trackedShips) {
        this.trackedShips = trackedShips;
    }
}
