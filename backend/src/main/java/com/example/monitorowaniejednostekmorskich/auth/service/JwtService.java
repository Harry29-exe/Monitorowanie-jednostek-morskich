package com.example.monitorowaniejednostekmorskich.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.monitorowaniejednostekmorskich.exceptions.auth.InvalidJwtTokeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final Algorithm jwtAlgorithm;

    private static final Long TWELVE_HOURS = 1000L * 60 * 60 * 12;

    public JwtService(@Value("${jwt-key}") String secret) {
        this.jwtAlgorithm = Algorithm.HMAC256(secret);
    }

    public String createToken(String username) {
        var now = new Date();

        return JWT.create()
                .withClaim("sub", username)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + TWELVE_HOURS))
                .sign(this.jwtAlgorithm);
    }

    public void verify(String token) {
        var jwt = JWT.decode(token);
        try {
            var username = jwt.getClaim("sub").asString();
            if(username.isBlank()) {
                throw new IllegalArgumentException();
            }
            var verifier = JWT.require(jwtAlgorithm)
                    .withClaim("sub", username)
                    .build();
            verifier.verify(jwt);
        } catch (JWTVerificationException ex) {
            throw new InvalidJwtTokeException();
        }
    }

}
