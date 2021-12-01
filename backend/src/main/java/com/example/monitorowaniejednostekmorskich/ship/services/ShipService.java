package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;

import java.util.List;

public interface ShipService {

    List<ShipDTO> getUsersShips(String username);

    List<ShipWithLocationDTO> getUsersStillTracked(String username);

    void addNewTackedShip(String username, Integer mmsi);

    void stopTracking(String username, Integer mmsi);

}
