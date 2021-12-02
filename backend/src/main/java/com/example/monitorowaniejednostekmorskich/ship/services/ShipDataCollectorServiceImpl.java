package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.CurrentShipInfoDTO;
import com.example.monitorowaniejednostekmorskich.AISAadapter.service.AISApiService;
import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;
import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipLocationRepository;
import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ShipDataCollectorServiceImpl implements ShipDataCollectorService {
    private final ShipRepository shipRepo;
    private final ShipLocationRepository shipLocationRepo;
    @Autowired
    @Lazy
    private AISApiService aisService;


    public ShipDataCollectorServiceImpl(ShipRepository shipRepo, ShipLocationRepository shipLocationRepo) {
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
            var currentShip = currentShipsMap.get(ship.getMmsi());
            if (currentShip != null) {
                var location = currentShip.getCurrentLocation();
                locationsToSave.add(new ShipLocalization(location.getTime(), location.getX(), location.getY(), ship));
            }
        }

        shipLocationRepo.saveAllAndFlush(locationsToSave);
    }

}
