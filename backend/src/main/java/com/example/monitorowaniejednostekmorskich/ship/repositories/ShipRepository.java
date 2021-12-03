package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long>, ShipDAO {

    ShipDTO findDTOByPublicId(UUID publicId);

    Ship findByPublicId(UUID publicId);

    List<ShipDTO> findAllByTrackedBy_UsernameAndStillTrackedTrue(String username);

    List<ShipDTO> findAllByTrackedBy_Username(String username);

    Ship findByMmsi(Integer mmsi);

    List<Ship> findAllByStillTrackedTrue();

    Boolean existsByMmsiAndTrackedBy_Username(Integer mmsi, String username);

}
