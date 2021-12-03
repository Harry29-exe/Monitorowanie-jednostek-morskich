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
        var em = sessionFactory.createEntityManager();
        var q = em.createQuery("""
                SELECT u
                FROM UserEntity u
                WHERE u.username = :username
                """, UserEntity.class);
        q.setParameter("username", username);

        var result = q.getSingleResult();
        em.flush();
        em.close();

        return Optional.of(result);
    }


    @Override
    public void save(UserEntity user) {
        var em = sessionFactory.createEntityManager();
        em.persist(user);
        em.flush();
        em.close();
    }
}
