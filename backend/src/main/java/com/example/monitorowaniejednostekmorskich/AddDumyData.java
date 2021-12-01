package com.example.monitorowaniejednostekmorskich;

import com.example.monitorowaniejednostekmorskich.ship.repositories.ShipRepository;
import com.example.monitorowaniejednostekmorskich.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddDumyData {
    @Autowired
    private UserService userService;
    @Autowired
    private ShipRepository shipRepository;

    @PostConstruct
    public void addBob() {
        userService.createNewUser("bob", "123");
//        shipRepository.save(new Ship(4234235))

    }

}
