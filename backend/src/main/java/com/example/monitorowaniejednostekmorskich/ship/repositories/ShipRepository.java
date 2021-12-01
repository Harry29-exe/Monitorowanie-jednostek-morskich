package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long>, ShipRepositoryExtension {

    ShipDTO findByName(String name);

    List<ShipDTO> findAllByTrackedBy_UsernameAndStillTrackedTrue(String username);

    List<ShipDTO> findAllByTrackedBy_Username(String username);

    Ship findByMmsi(Integer mmsi);

}
