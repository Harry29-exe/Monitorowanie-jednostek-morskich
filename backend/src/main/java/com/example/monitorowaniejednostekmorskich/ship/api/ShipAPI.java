package com.example.monitorowaniejednostekmorskich.ship.api;

import com.example.monitorowaniejednostekmorskich.ship.api.responses.GetShipHistoryResponse;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/ships/")
public interface ShipAPI {

    @PostMapping("history/{shipId}")
    GetShipHistoryResponse getShipHistory(@PathVariable Long shipId);

    @GetMapping("tracked-ships")
    List<ShipWithLocationDTO> getTrackedShips(Authentication auth);

    @DeleteMapping("tracking/{shipId}")
    void stopTrackingShip(@PathVariable Long shipId);

    @PostMapping("tracking/{shipMMSI}")
    void startTrackingShip(@PathVariable Integer shipMMSI);

}
