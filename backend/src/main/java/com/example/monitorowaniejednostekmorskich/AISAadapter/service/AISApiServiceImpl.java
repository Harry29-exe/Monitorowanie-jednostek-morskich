package com.example.monitorowaniejednostekmorskich.AISAadapter.service;

import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.CurrentShipInfoDTO;
import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.ShipTrackAIS;
import com.example.monitorowaniejednostekmorskich.ship.ShipTypeConverter;
import com.example.monitorowaniejednostekmorskich.ship.dto.AreaDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.LocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.services.ShipDataCollectorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AISApiServiceImpl implements AISApiService {
    private final ShipDataCollectorService shipDataCollectorService;
    private final AtomicReference<String> apiSecret = new AtomicReference<>();
    private final AtomicReference<String> accessToken = new AtomicReference<>();
    private final RestTemplate template = new RestTemplate();

    private List<CurrentShipInfoDTO> currentShips = new ArrayList<>();

    private static final Integer TEN_MINUTES = 600_000;

    public AISApiServiceImpl(
            @Value("${MJM_secret}") String secret,
            ShipDataCollectorService shipDataCollectorService) {
        this.shipDataCollectorService = shipDataCollectorService;
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("No secret in environment");
        }
        this.apiSecret.set(secret);
        this.fetchAuthToken();
    }

    @Override
    public List<CurrentShipInfoDTO> getAllShips() {
        return currentShips;
    }

    @Override
    public @Nullable
    CurrentShipInfoDTO getShip(Integer mmsi) {
        return currentShips.stream()
                .filter(s -> s.getMmsi().equals(mmsi))
                .findFirst()
                .orElse(null);
    }

    //    600_000
    @Scheduled(initialDelay = 100, fixedDelay = 60_000)
    public void fetchCurrentShips() {
        this.currentShips = fetchShipFromArea(new AreaDTO(-0.79, 50.0, 34.36, 71.81));
        this.shipDataCollectorService.onAISUpdate();
    }

    @Scheduled(initialDelay = 3_000_000, fixedDelay = 3_000_000)
    public void fetchAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "wojcikakamil@gmail.com:Backend");
        body.add("scope", "api");
        body.add("client_secret", this.apiSecret.get());
        body.add("grant_type", "client_credentials");

        HttpEntity entity = new HttpEntity(body, headers);
        var response = template.exchange("https://id.barentswatch.no/connect/token",
                HttpMethod.POST,
                entity,
                Map.class);
        var responseBody = response.getBody();
        if (responseBody == null) {
            throw new IllegalStateException();
        }
        var token = responseBody.get("access_token");
        if (token == null) {
            throw new IllegalStateException();
        }
        this.accessToken.set((String) token);
    }

    private List<CurrentShipInfoDTO> fetchShipFromArea(AreaDTO area) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken.get());
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<ShipTrackAIS[]> response = template.exchange(
                "https://www.barentswatch.no/bwapi/v2/geodata/ais/openpositions?" +
                        "Xmin=" + area.getFromX() + "&Xmax=" + area.getToX() +
                        "&Ymin=" + area.getFromY() + "&Ymax=" + area.getToY(),
//                "https://www.barentswatch.no/bwapi/v2/geodata/ais/openpositions?Xmin=10.09094&Xmax=10.67047&Ymin=63.3989&Ymax=63.58645",
                HttpMethod.GET,
                httpEntity,
                ShipTrackAIS[].class
        );
        if (response.getBody() == null) {
            throw new IllegalStateException();
        }

        var responseBody = response.getBody();
        List<CurrentShipInfoDTO> ships = new ArrayList<>(responseBody.length);
        for (var shipTrack : responseBody) {
            var shipGeo = shipTrack.getGeometry();
            var ship = new CurrentShipInfoDTO(
                    shipTrack.getMmsi(),
                    shipTrack.getName(),
                    ShipTypeConverter.convertShipType(shipTrack.getShipType().longValue()),
                    shipTrack.getDestination(),
                    new LocationDTO(
                            shipGeo.getCoordinates().get(0),
                            shipGeo.getCoordinates().get(1),
                            new Date())
            );

            ships.add(ship);
        }

        return ships;
    }
}
