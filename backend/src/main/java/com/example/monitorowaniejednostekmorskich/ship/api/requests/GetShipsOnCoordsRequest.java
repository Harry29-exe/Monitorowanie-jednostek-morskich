package com.example.monitorowaniejednostekmorskich.ship.api.requests;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value
public class GetShipsOnCoordsRequest {

    @NonNull
    Integer fromX;
    @NonNull
    Integer fromY;
    @NonNull
    Integer toX;
    @NonNull
    Integer toY;

}
