package com.example.monitorowaniejednostekmorskich.AISAadapter.service;

import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.CurrentShipInfoDTO;

import java.util.List;

public interface AISService {

    List<CurrentShipInfoDTO> getAllShips();

    CurrentShipInfoDTO getShip(Integer mmsi);

}
