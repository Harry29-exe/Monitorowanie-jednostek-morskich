package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;

import java.util.List;
import java.util.UUID;

public interface ShipLocationDAO {

    List<ShipLocalization> findAllByShip_PublicIdOrderByTime(UUID shipId);

    void saveAll(Iterable<ShipLocalization> localizations);

    void save(ShipLocalization shipLocalization);

}
