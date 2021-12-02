package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.ship.dto.LocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;

import java.util.List;
import java.util.UUID;

public interface ShipService {

    List<ShipDTO> getUsersShips(String username);

    List<ShipWithLocationDTO> getUsersStillTracked(String username);

    List<LocationDTO> getShipLocations(UUID shipPublicId);

    ShipDTO getShip(UUID publicId);

    void addNewTackedShip(String username, Integer mmsi);

    void stopTracking(String username, Integer mmsi);

}
