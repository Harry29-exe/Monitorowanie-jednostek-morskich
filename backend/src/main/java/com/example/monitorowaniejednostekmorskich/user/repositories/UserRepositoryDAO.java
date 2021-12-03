package com.example.monitorowaniejednostekmorskich.user.repositories;

import com.example.monitorowaniejednostekmorskich.user.entity.UserEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryDAO implements UserDAO {
    private final SessionFactory sessionFactory;

    public UserRepositoryDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Optional<UserEntity> findByUsername(String username) {
        var session = sessionFactory.openSession();
        var t = session.beginTransaction();

        var q = session.createQuery("""
                SELECT u
                FROM UserEntity u
                WHERE u.username = :username
                """, UserEntity.class);
        q.setParameter("username", username);

        var result = q.getSingleResult();
        session.flush();
        t.commit();
        session.close();

        return Optional.of(result);
    }


    @Override
    public void save(UserEntity user) {
        var session = sessionFactory.openSession();
        var t = session.beginTransaction();

        session.save(user);

//        session.flush();
        t.commit();
        session.close();
    }
}
