package com.example.monitorowaniejednostekmorskich.user.service;

import com.example.monitorowaniejednostekmorskich.user.entity.UserEntity;
import com.example.monitorowaniejednostekmorskich.user.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createNewUser(String username, String nonEncodedPassword) {
        this.validateUsername(username);
        var password = passwordEncoder.encode(nonEncodedPassword);
        var userEntity = new UserEntity(username, password);

        userRepo.saveAndFlush(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = userRepo.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
    }

    private void validateUsername(String username) {
        for (char c : username.toCharArray()) {
            if(!Character.isLetterOrDigit(c)) {
                throw new BadCredentialsException("Username can only contains letters and digits");
            }
        }
    }

}
