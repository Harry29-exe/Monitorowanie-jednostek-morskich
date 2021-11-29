package com.example.monitorowaniejednostekmorskich.auth.api.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class LoginRequest {

    @NotBlank String username;
    @NotBlank String password;

}
