package com.example.monitorowaniejednostekmorskich.ship.services;

import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipRepository;
import com.example.monitorowaniejednostekmorskich.user.dto.UserDTO;
import com.example.monitorowaniejednostekmorskich.user.repositories.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    public List<ShipDTO> getStillTrackedBy(UserDTO user) {
        return shipRepo.findAllByTrackedBy_UsernameAndStillTrackedTrue(user.getUsername());
    }

    @Override
    public List<ShipDTO> getAllUsersShips(UserDTO user) {
        return shipRepo.findAllByTrackedBy_Username(user.getUsername());
    }

    @Override
    public void addNewTackedShip(UserDTO user, Integer mmsi) {
        var userEntity = userRepo.findByUsername(user.getUsername())
                .orElseThrow(EntityNotFoundException::new);
        var ship = new Ship(mmsi, userEntity);
        shipRepo.save(ship);
    }

    @Override
    public void stopTracking(UserDTO user, Integer mmsi) {
        var ship = shipRepo.findByMmsi(mmsi);
        ship.setStillTracked(false);
        shipRepo.save(ship);
    }
}
