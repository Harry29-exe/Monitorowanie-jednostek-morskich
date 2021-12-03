package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ShipDAO {

    ShipWithLocationDTO findDTOByMMSI(Integer mmsi);

    List<ShipWithLocationDTO> findAllUsersShip(String username);

}
