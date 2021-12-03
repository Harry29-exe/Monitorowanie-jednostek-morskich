package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;

import java.util.List;
import java.util.UUID;

public interface ShipDAO {

//    ShipWithLocationDTO findDTOByMMSI(Integer mmsi);

    List<ShipWithLocationDTO> findAllUsersShip(String username);

    ShipDTO findDTOByPublicId(UUID publicId);

    Ship findByPublicId(UUID publicId);

    List<ShipDTO> findAllByTrackedBy_UsernameAndStillTrackedTrue(String username);

    List<Ship> findAllByStillTrackedTrue();

    Boolean existsByMmsiAndTrackedBy_Username(Integer mmsi, String username);

    void save(Ship ship);

    void delete(Ship ship);

}
