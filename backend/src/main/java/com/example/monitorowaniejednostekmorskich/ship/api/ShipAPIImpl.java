package com.example.monitorowaniejednostekmorskich.ship.api;

import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.CurrentShipInfoDTO;
import com.example.monitorowaniejednostekmorskich.AISAadapter.service.AISApiService;
import com.example.monitorowaniejednostekmorskich.ship.api.responses.GetShipHistoryResponse;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ShipAPIImpl implements ShipAPI {
    private final AISApiService aisService;

    public ShipAPIImpl(AISApiService aisService) {
        this.aisService = aisService;
    }

    @Override
    public List<CurrentShipInfoDTO> getAllShips() {
        return aisService.getAllShips();
    }

    @Override
    public GetShipHistoryResponse getShipHistory(Long shipId) {
        return null;
    }

    @Override
    public List<ShipWithLocationDTO> getTrackedShips(Authentication auth) {
        return null;
    }

    @Override
    public void stopTrackingShip(Long shipId) {

    }

    @Override
    public void startTrackingShip(Integer shipMMSI) {

    }
}
