package com.example.monitorowaniejednostekmorskich.ship.api;

import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.CurrentShipInfoDTO;
import com.example.monitorowaniejednostekmorskich.config.CorsAddresses;
import com.example.monitorowaniejednostekmorskich.ship.api.responses.GetShipHistoryResponse;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = CorsAddresses.FRONTEND_ADDRESS, allowedHeaders = {"*", "Authorization"}, exposedHeaders = "*", allowCredentials = "true")
@RequestMapping("/ships/")
public interface ShipAPI {

    @GetMapping("all")
    List<CurrentShipInfoDTO> getAllShips();

    @PostMapping("history/{shipId}")
    @PreAuthorize("isAuthenticated()")
    GetShipHistoryResponse getShipHistory(@PathVariable UUID shipId);

    @GetMapping("tracked-ships")
    @PreAuthorize("isAuthenticated()")
    List<ShipWithLocationDTO> getTrackedShips(Authentication auth);

    @DeleteMapping("tracking/{shipId}")
    @PreAuthorize("isAuthenticated()")
    void stopTrackingShip(@PathVariable UUID shipId, Authentication auth);

    @PostMapping("tracking/{shipMMSI}")
    @PreAuthorize("isAuthenticated()")
    void startTrackingShip(@PathVariable Integer shipMMSI, Authentication auth);

    @DeleteMapping("/{shipId}")
    @PreAuthorize("isAuthenticated()")
    void deleteShip(@PathVariable UUID shipId, Authentication auth);

}
