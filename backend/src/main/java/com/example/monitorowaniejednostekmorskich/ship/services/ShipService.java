package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import com.example.monitorowaniejednostekmorskich.user.dto.UserDTO;

import java.util.List;

public interface ShipService {

    List<ShipDTO> getStillTrackedBy(UserDTO user);

    List<ShipDTO> getAllUsersShips(UserDTO user);

    void addNewTackedShip(UserDTO user, Integer mmsi);

    void stopTracking(UserDTO user, Integer mmsi);

}
