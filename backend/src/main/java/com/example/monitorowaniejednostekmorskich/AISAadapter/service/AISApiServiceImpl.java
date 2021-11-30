package com.example.monitorowaniejednostekmorskich.AISAadapter.service;

import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.CurrentShipInfoDTO;
import com.example.monitorowaniejednostekmorskich.AISAadapter.dto.ShipTrackAIS;
import com.example.monitorowaniejednostekmorskich.ship.ShipTypeConverter;
import com.example.monitorowaniejednostekmorskich.ship.dto.AreaDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.LocationDTO;
import com.example.monitorowaniejednostekmorskich.ship.dto.ShipDTO;
import jdk.jshell.spi.ExecutionControl;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AISApiServiceImpl implements AISApiService {
    private final String apiKey;
    private AtomicReference<String> accessToken;
    private final RestTemplate template = new RestTemplate();

    private List<CurrentShipInfoDTO> currentShips = new ArrayList<>();

    private static final Integer TEN_MINUTES = 60 * 10 * 1000;

    public AISApiServiceImpl(@Value("${AIS.secret}") String apiKey) {
        this.apiKey = apiKey;
        this.fetchCurrentShips();
    }

    @Override
    public List<CurrentShipInfoDTO> getAllShips() {
        return currentShips;
    }

    @Override
    public ShipDTO getShip(Integer mmsi) {
        throw  new NotYetImplementedException();
    }

    @Scheduled(fixedDelay = TEN_MINUTES)
    public void fetchCurrentShips() {
        this.currentShips = fetchShipFromArea(new AreaDTO(71.815, 34.36, 63.568, -0.791));
    }

    private List<CurrentShipInfoDTO> fetchShipFromArea(AreaDTO area) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<ShipTrackAIS[]> response = template.exchange(
                "https://www.barentswatch.no/bwapi/v2/geodata/ais/openpositions?" +
                        "Xmin="+area.getFromX()+"&Xmax="+area.getToX()+
                        "&Ymin="+area.getFromY()+"&Ymax="+area.getToY(),
                HttpMethod.GET,
                httpEntity,
                ShipTrackAIS[].class
        );
        if(response.getBody() == null) {
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
