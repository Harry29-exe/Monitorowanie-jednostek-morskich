package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.CurrentShipInfoDTO;
import com.example.monitorowaniejednostekmorskich.AISAadapter.service.AISService;
import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;
import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipDAO;
import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipLocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ShipDataCollectorServiceImpl implements ShipDataCollectorService {
    private final ShipDAO shipRepo;
    private final ShipLocationDAO shipLocationRepo;
    @Autowired
    @Lazy
    private AISService aisService;


    public ShipDataCollectorServiceImpl(ShipDAO shipRepo, ShipLocationDAO shipLocationRepo) {
        this.shipRepo = shipRepo;
        this.shipLocationRepo = shipLocationRepo;
    }

    @Override
    public void onAISUpdate() {
        var allShips = shipRepo.findAllByStillTrackedTrue();
        var allCurrentShips = aisService.getAllShips();
        var currentShipsMap = new HashMap<Integer, CurrentShipInfoDTO>();
        allCurrentShips.forEach(s -> currentShipsMap.put(s.getMmsi(), s));

        var locationsToSave = new ArrayList<ShipLocalization>();
        for (var ship : allShips) {
            if (!ship.getStillTracked()) {
                continue;
            }
            var currentShip = currentShipsMap.get(ship.getMmsi());
            if (currentShip != null) {
                var location = currentShip.getCurrentLocation();
                locationsToSave.add(new ShipLocalization(location.getTime(), location.getX(), location.getY(), ship));
            }
        }

        shipLocationRepo.saveAll(locationsToSave);
    }

}
