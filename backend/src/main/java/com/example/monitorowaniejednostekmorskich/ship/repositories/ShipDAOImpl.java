package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.dto.LocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipWithLocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.entity.Ship;
import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShipDAOImpl implements ShipDAO {
    private final SessionFactory sessionFactory;

    public ShipDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<ShipWithLocationDTO> findAllUsersShip(String username) {
        var session = sessionFactory.openSession();
        var t = session.beginTransaction();

        var query = session.createQuery("""
                SELECT s
                FROM Ship s
                WHERE s.trackedBy.username = :username
                """, Ship.class);
        query.setParameter("username", username);
        var ships = query.getResultList();
        List<ShipWithLocationDTO> shipsWithLocations = new ArrayList<>();

        for (var ship : ships) {
            var locationQuery = session.createQuery("""
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

        session.flush();
        t.commit();
        session.close();

        return shipsWithLocations;
    }

    @Override
    public ShipDTO findDTOByPublicId(UUID publicId) {
        var ship = this.findByPublicId(publicId);

        return new ShipDTO(ship.getPublicId(), ship.getMmsi(), ship.getShipType(), ship.getName(), ship.getStillTracked());
    }

    @Override
    public Ship findByPublicId(UUID publicId) {
        var session = sessionFactory.openSession();
        var t = session.beginTransaction();

        var q = session.createQuery("""
                SELECT s
                FROM Ship s
                WHERE s.publicId = :publicId
                """, Ship.class);
        q.setParameter("publicId", publicId);
        var ship = q.getSingleResult();

        session.flush();
        t.commit();
        session.close();

        return ship;
    }

    @Override
    public List<ShipDTO> findAllByTrackedBy_UsernameAndStillTrackedTrue(String username) {
        var session = sessionFactory.openSession();
        var t = session.beginTransaction();

        var q = session.createQuery("""
                SELECT s
                FROM Ship s
                WHERE s.stillTracked = true AND s.trackedBy.username = :username
                """, Ship.class);
        q.setParameter("username", username);
        var ship = q.getResultList();

        session.flush();
        t.commit();
        session.close();

        return ship.stream().map(ShipDTO::from).toList();
    }

    @Override
    public List<Ship> findAllByStillTrackedTrue() {
        var session = sessionFactory.openSession();
        var t = session.beginTransaction();

        var q = session.createQuery("""
                SELECT s
                FROM Ship s
                WHERE s.stillTracked = true
                """, Ship.class);
        var ship = q.getResultList();

        session.flush();
        t.commit();
        session.close();

        return ship;
    }

    @Override
    public Boolean existsByMmsiAndTrackedBy_Username(Integer mmsi, String username) {
        var session = sessionFactory.openSession();
        var t = session.beginTransaction();

        var q = session.createQuery("""
                SELECT s
                FROM Ship s
                WHERE s.mmsi = :mmsi AND s.trackedBy.username = :username
                """, Ship.class);
        q.setParameter("mmsi", mmsi);
        q.setParameter("username", username);

        Ship ship = null;
        try {
            ship = q.getSingleResult();
        } catch (NoResultException ignored) {

        }

        session.flush();
        t.commit();
        session.close();

        return ship != null;
    }

    @Override
    public void save(Ship ship) {
        var session = sessionFactory.openSession();
        var t = session.beginTransaction();

        session.save(ship);

        session.flush();
        t.commit();
        session.close();

    }

    @Override
    public void delete(Ship ship) {
        var session = sessionFactory.openSession();
        var t = session.beginTransaction();

        session.remove(ship);
        session.flush();
        t.commit();
        session.close();
    }
}

