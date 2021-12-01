package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipRepository;
import com.example.monitorowaniejednostekmorskich.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class ShipServiceImpl implements ShipService {
    private final ShipRepository shipRepo;
    private final UserRepository userRepo;

    public ShipServiceImpl(ShipRepository shipRepository, UserRepository userRepo) {
        this.shipRepo = shipRepository;
        this.userRepo = userRepo;
    }

    @Override
    public List<ShipDTO> getUsersShips(String username) {
        return shipRepo.findAllByTrackedBy_UsernameAndStillTrackedTrue(username);
    }

    @Override
    public List<ShipWithLocationDTO> getUsersStillTracked(String username) {
        return shipRepo.findAllUsersShip(username);
    }

    @Override
    public void addNewTackedShip(String username, Integer mmsi) {
        var userEntity = userRepo.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        var ship = new Ship(mmsi, userEntity);
        shipRepo.save(ship);
    }

    @Override
    public void stopTracking(String username, Integer mmsi) {
        var ship = shipRepo.findByMmsi(mmsi);
        ship.setStillTracked(false);
        shipRepo.save(ship);
    }
}
