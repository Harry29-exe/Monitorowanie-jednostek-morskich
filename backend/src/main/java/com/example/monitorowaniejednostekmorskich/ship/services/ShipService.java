package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;

import java.util.List;

public interface ShipService {

    List<ShipDTO> getStillTrackedBy(Long userId);

    List<ShipDTO> getAllUsersShips(Long userId);

    void addNewTackedShip(Long userId, Integer mmsi);

    void stopTracking(Long userId, Integer mmsi);

}
