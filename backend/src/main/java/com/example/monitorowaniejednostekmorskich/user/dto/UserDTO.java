package com.example.monitorowaniejednostekmorskich.user.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Value
public class UserDTO {

    @NonNull Long id;
    @NonNull String username;

}
