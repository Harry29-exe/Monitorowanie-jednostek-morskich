package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShipLocationRepository extends JpaRepository<ShipLocalization, Long> {

    List<ShipLocalization> findAllByShip_PublicIdOrderByTime(UUID shipId);

}
