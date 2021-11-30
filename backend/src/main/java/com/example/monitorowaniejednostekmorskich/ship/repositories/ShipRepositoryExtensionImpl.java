package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ShipRepositoryExtensionImpl implements ShipRepositoryExtension {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ShipWithLocationDTO findDTOByMMSI(Integer mmsi) {
        var q = em.createQuery("""
            SELECT s
            FROM Ship s
            WHERE s.mmsi = :mmsi
            """);
        q.setParameter("mmsi", mmsi);
        var ship = (Ship) q.getSingleResult();
//        var lastLocation = em.createQuery("""
//            SELECT
//            """)

        return null;
    }
}
