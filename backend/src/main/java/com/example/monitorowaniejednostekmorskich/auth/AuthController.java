package com.example.monitorowaniejednostekmorskich.auth;

import com.example.monitorowaniejednostekmorskich.config.CorsAddresses;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(CorsAddresses.FRONTEND_ADDRESS)
public class AuthController {

    @GetMapping("/login")
    public void login() {

    }

    @PostMapping("/register")
    public void register() {

    }


}
