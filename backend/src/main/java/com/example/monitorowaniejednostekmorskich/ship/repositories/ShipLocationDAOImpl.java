package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShipLocationDAOImpl implements ShipLocationDAO {
    private final SessionFactory sessionFactory;

    public ShipLocationDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<ShipLocalization> findAllByShip_PublicIdOrderByTime(UUID shipId) {
        var session = sessionFactory.openSession();
        var t = session.getTransaction();
        t.begin();

        var q = session.createQuery("""
                SELECT l
                FROM ShipLocalization l
                WHERE l.ship.publicId = :shipId
                """, ShipLocalization.class);
        q.setParameter("shipId", shipId);
        var results = q.getResultList();

        session.flush();
        t.commit();
        session.close();

        return results;
    }

    @Override
    public void saveAll(Iterable<ShipLocalization> localizations) {
        var session = sessionFactory.openSession();
        var t = session.getTransaction();
        t.begin();

        for (var l : localizations) {
            session.save(l);
        }

        session.flush();
        t.commit();
        session.close();
    }

    @Override
    public void save(ShipLocalization shipLocalization) {
        var session = sessionFactory.openSession();
        var t = session.getTransaction();
        t.begin();

        session.save(shipLocalization);

        session.flush();
        t.commit();
        session.close();
    }
}
