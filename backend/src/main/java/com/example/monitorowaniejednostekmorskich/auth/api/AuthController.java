package com.example.monitorowaniejednostekmorskich.auth.api;

import com.example.monitorowaniejednostekmorskich.auth.api.dto.LoginRequest;
import com.example.monitorowaniejednostekmorskich.auth.api.dto.RegisterRequest;
import com.example.monitorowaniejednostekmorskich.auth.service.JwtService;
import com.example.monitorowaniejednostekmorskich.config.CorsAddresses;
import com.example.monitorowaniejednostekmorskich.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@CrossOrigin(CorsAddresses.FRONTEND_ADDRESS)
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserService userService;

    public AuthController(JwtService jwtService, AuthenticationManager authManager, UserService userService) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequest request, HttpServletResponse response) {
        try {
            var auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()));
            var token = jwtService.createToken(((User) auth.getPrincipal()).getUsername());
            response.addHeader("Authorization", token);
        } catch (Exception ex) {
            throw new BadCredentialsException("Bad username or password");
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody @Valid RegisterRequest request) {
        userService.createNewUser(request.getUsername(), request.getPassword());
    }


}
