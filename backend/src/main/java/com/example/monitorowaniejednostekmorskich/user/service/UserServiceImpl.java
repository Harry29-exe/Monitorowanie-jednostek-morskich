package com.example.monitorowaniejednostekmorskich.user.service;

import com.example.monitorowaniejednostekmorskich.user.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = userRepo.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
    }
}
