package com.example.monitorowaniejednostekmorskich.user.repositories;

import com.example.monitorowaniejednostekmorskich.user.entity.UserEntity;

import java.util.Optional;

public interface UserDAO {

    Optional<UserEntity> findByUsername(String username);

    void save(UserEntity user);

}
