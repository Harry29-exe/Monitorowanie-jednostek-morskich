package com.example.monitorowaniejednostekmorskich.ship.repositories;

import com.example.monitorowaniejednostekmorskich.ship.entity.ShipLocalization;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class ShipLocationDAO {
    private final SessionFactory sessionFactory;

    public ShipLocationDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveAllAndFlush(Iterable<ShipLocalization> localizations) {

    }

}
