package com.example.monitorowaniejednostekmorskich.AISAadapter.service;

import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.CurrentShipInfoDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.AreaDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;

import java.net.URISyntaxException;
import java.util.List;

public interface AISApiService {

    List<CurrentShipInfoDTO> getAllShips();

    ShipDTO getShip(Integer mmsi);

}
