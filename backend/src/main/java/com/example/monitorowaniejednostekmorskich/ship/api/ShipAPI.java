package com.example.monitorowaniejednostekmorskich.ship.api;

import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.CurrentShipInfoDTO;
import com.example.monitorowaniejednostekmorskich.config.CorsAddresses;
import com.example.monitorowaniejednostekmorskich.ship.api.responses.GetShipHistoryResponse;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = CorsAddresses.FRONTEND_ADDRESS)
@RequestMapping("/ships/")
public interface ShipAPI {

    @GetMapping("all")
    List<CurrentShipInfoDTO> getAllShips();

    @PostMapping("history/{shipId}")
    @PreAuthorize("isAuthenticated()")
    GetShipHistoryResponse getShipHistory(@PathVariable Long shipId);

    @GetMapping("tracked-ships")
    @PreAuthorize("isAuthenticated()")
    List<ShipWithLocationDTO> getTrackedShips(Authentication auth);

    @DeleteMapping("tracking/{shipId}")
    @PreAuthorize("isAuthenticated()")
    void stopTrackingShip(@PathVariable Long shipId, Authentication auth);

    @PostMapping("tracking/{shipMMSI}")
    @PreAuthorize("isAuthenticated()")
    void startTrackingShip(@PathVariable Integer shipMMSI, Authentication auth);

}
