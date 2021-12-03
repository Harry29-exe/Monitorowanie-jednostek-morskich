package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.AISAadapter.service.AISService;
import com.example.monitorowaniejednostekmorskich.ship.dto.LocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;
import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipLocationRepository;
import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipRepository;
import com.example.monitorowaniejednostekmorskich.user.repositories.UserRepository;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ShipServiceImpl implements ShipService {
    private final ShipRepository shipRepo;
    private final UserRepository userRepo;
    private final AISService aisService;
    private final ShipLocationRepository shipLocationRepo;

    public ShipServiceImpl(ShipRepository shipRepository, UserRepository userRepo, AISService aisService, ShipLocationRepository shipLocationRepo) {
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
        return shipRepo.findDTOByPublicId(publicId);
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
        if (shipRepo.existsByMmsiAndTrackedBy_Username(mmsi, username)) {
            throw new EntityExistsException("Ship is already being tracked");
        }


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
    public void stopTracking(String username, UUID shipPublicId) {
        var ship = shipRepo.findByPublicId(shipPublicId);
        ship.setStillTracked(false);
        shipRepo.save(ship);
    }

    @Override
    public void deleteShip(String username, UUID shipPublicId) {
        var ship = shipRepo.findByPublicId(shipPublicId);
        if (!ship.getTrackedBy().getUsername().equals(username)) {
            throw new AuthorizationServiceException("");
        }
        shipRepo.delete(ship);
    }
}
