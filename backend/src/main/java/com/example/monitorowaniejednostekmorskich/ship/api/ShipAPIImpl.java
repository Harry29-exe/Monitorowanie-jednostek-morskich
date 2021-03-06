package com.example.monitorowaniejednostekmorskich.ship.api;

import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.CurrentShipInfoDTO;
import com.example.monitorowaniejednostekmorskich.AISAadapter.service.AISService;
import com.example.monitorowaniejednostekmorskich.ship.api.responses.GetShipHistoryResponse;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.services.ShipService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class ShipAPIImpl implements ShipAPI {
    private final AISService aisService;
    private final ShipService shipService;

    public ShipAPIImpl(AISService aisService, ShipService shipService) {
        this.aisService = aisService;
        this.shipService = shipService;
    }

    @Override
    public List<CurrentShipInfoDTO> getAllShips() {
        return aisService.getAllShips();
    }

    @Override
    public GetShipHistoryResponse getShipHistory(UUID shipId) {
        var ship = shipService.getShip(shipId);
        var locations = shipService.getShipLocations(shipId);
        return new GetShipHistoryResponse(ship, locations);
    }

    @Override
    public List<ShipWithLocationDTO> getTrackedShips(Authentication auth) {
        if (auth instanceof UsernamePasswordAuthenticationToken upAuth) {
            return shipService.getUsersStillTracked(upAuth.getName());
        }
        throw new IllegalStateException();
    }

    @Override
    public void stopTrackingShip(UUID shipId, Authentication auth) {
        if (auth instanceof UsernamePasswordAuthenticationToken upAuth) {
            shipService.stopTracking(upAuth.getName(), shipId);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void startTrackingShip(Integer shipMMSI, Authentication auth) {
        if (auth instanceof UsernamePasswordAuthenticationToken upAuth) {
            shipService.addNewTackedShip(upAuth.getName(), shipMMSI);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void deleteShip(UUID shipId, Authentication auth) {
        if (auth instanceof UsernamePasswordAuthenticationToken upAuth) {
            shipService.deleteShip(upAuth.getName(), shipId);
        } else {
            throw new IllegalStateException();
        }
    }
}
