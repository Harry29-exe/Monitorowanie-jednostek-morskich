package com.example.monitorowaniejednostekmorskich.ship.api;

import com.example.monitorowaniejednostekmorskich.ship.api.requests.GetShipsOnCoordsRequest;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/AIS/")
public interface ShipAISBridgeAPI {

    @PostMapping("on-coords")
    List<ShipDTO> getShipsWithCoords(@RequestBody @Valid GetShipsOnCoordsRequest request);

}
