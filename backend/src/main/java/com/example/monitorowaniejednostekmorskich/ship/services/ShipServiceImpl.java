package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.AISAadapter.service.AISApiService;
import com.example.monitorowaniejednostekmorskich.ship.dto.LocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;
import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipLocationRepository;
import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipRepository;
import com.example.monitorowaniejednostekmorskich.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ShipServiceImpl implements ShipService {
    private final ShipRepository shipRepo;
    private final UserRepository userRepo;
    private final AISApiService aisService;
    private final ShipLocationRepository shipLocationRepo;

    public ShipServiceImpl(ShipRepository shipRepository, UserRepository userRepo, AISApiService aisService, ShipLocationRepository shipLocationRepo) {
        this.shipRepo = shipRepository;
        this.userRepo = userRepo;
        this.aisService = aisService;
        this.shipLocationRepo = shipLocationRepo;
    }

    @Override
    public List<ShipDTO> getUsersShips(String username) {
        return shipRepo.findAllByTrackedBy_UsernameAndStillTrackedTrue(username);
    }

    @Override
    public ShipDTO getShip(UUID publicId) {
        return shipRepo.findByPublicId(publicId);
    }

    @Override
    public List<ShipWithLocationDTO> getUsersStillTracked(String username) {
        return shipRepo.findAllUsersShip(username);
    }

    @Override
    public List<LocationDTO> getShipLocations(UUID shipPublicId) {
        return shipLocationRepo.findAllByShip_PublicIdOrderByTime(shipPublicId)
                .stream().map(LocationDTO::from).toList();
    }

    @Override
    public void addNewTackedShip(String username, Integer mmsi) {
        var userEntity = userRepo.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        var currentShip = aisService.getShip(mmsi);

        if (currentShip == null) {
            Ship ship = new Ship(mmsi, userEntity);
            shipRepo.save(ship);
        } else {
            Ship ship = new Ship(mmsi, currentShip.getType(), currentShip.getName(), userEntity);
            shipRepo.saveAndFlush(ship);

            ShipLocalization localization = new ShipLocalization(
                    currentShip.getCurrentLocation().getTime(),
                    currentShip.getCurrentLocation().getX(),
                    currentShip.getCurrentLocation().getY(),
                    ship
            );
            shipLocationRepo.save(localization);
        }
    }

    @Override
    public void stopTracking(String username, Integer mmsi) {
        var ship = shipRepo.findByMmsi(mmsi);
        ship.setStillTracked(false);
        shipRepo.save(ship);
    }


}
