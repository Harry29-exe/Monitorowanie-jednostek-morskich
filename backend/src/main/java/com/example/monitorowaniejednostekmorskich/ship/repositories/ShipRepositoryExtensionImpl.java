package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.dto.LocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShipRepositoryExtensionImpl implements ShipDAO {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

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

        throw new NotYetImplementedException();
    }

    @Override
    public List<ShipWithLocationDTO> findAllUsersShip(String username) {
        var em = entityManagerFactory.createEntityManager();
        var query = em.createQuery("""
                SELECT s
                FROM Ship s
                WHERE s.trackedBy.username = :username
                """, Ship.class);
        query.setParameter("username", username);
        var ships = query.getResultList();
        List<ShipWithLocationDTO> shipsWithLocations = new ArrayList<>();

        for (var ship : ships) {
            var locationQuery = em.createQuery("""
                    SELECT l
                    FROM ShipLocalization l
                    WHERE l.ship.id = :id
                    ORDER BY l.time DESC
                    """, ShipLocalization.class);
            locationQuery
                    .setMaxResults(1)
                    .setParameter("id", ship.getId());
            var lastLocation = locationQuery.getResultList();
            if (lastLocation.size() == 0) {
                shipsWithLocations.add(new ShipWithLocationDTO(
                        ShipDTO.from(ship), null
                ));
            } else {
                shipsWithLocations.add(new ShipWithLocationDTO(
                        ShipDTO.from(ship), LocationDTO.from(lastLocation.get(0))
                ));
            }
        }

        em.close();

        return shipsWithLocations;
    }


}
