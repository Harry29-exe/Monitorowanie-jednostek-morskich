package com.example.monitorowaniejednostekmorskich.ship.api.responses;

import com.example.monitorowaniejednostekmorskich.ship.dto.LocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import lombok.Value;

import java.util.List;

@Value
public class GetShipHistoryResponse {

    ShipDTO ship;
    List<LocationDTO> history;

}
