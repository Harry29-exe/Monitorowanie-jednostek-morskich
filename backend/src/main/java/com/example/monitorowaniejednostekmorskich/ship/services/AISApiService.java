package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.ship.dto.AreaDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;

import java.util.List;

public interface AISApiService {

    List<ShipDTO> fetchShipFromArea(AreaDTO area);

    ShipDTO getShip(Integer mmsi);

}
