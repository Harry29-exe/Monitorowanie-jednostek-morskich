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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AISApiServiceImpl implements AISApiService {
    private final String apiKey;
    private final AtomicReference<String> accessToken = new AtomicReference<>();
    private final RestTemplate template = new RestTemplate();

    private List<CurrentShipInfoDTO> currentShips = new ArrayList<>();

    private static final Integer TEN_MINUTES = 600_000;

    public AISApiServiceImpl(@Value("${AIS.token}") String apiKey) {
//        this.apiKey = apiKey;
        this.apiKey = "";
        accessToken.set(apiKey);
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

    @Scheduled(fixedDelay = 600_000)
    public void fetchCurrentShips() {
        this.currentShips = fetchShipFromArea(new AreaDTO(34.36, 71.815,   -0.791, 63.568));
    }

    private List<CurrentShipInfoDTO> fetchShipFromArea(AreaDTO area) {
        HttpHeaders headers = new HttpHeaders();
        System.out.println(accessToken.get());
        headers.add("Authorization", "Bearer " + accessToken.get());
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<ShipTrackAIS[]> response = template.exchange(
//                "https://www.barentswatch.no/bwapi/v2/geodata/ais/openpositions?" +
//                        "Xmin="+area.getFromX()+"&Xmax="+area.getToX()+
//                        "&Ymin="+area.getFromY()+"&Ymax="+area.getToY(),
                "https://www.barentswatch.no/bwapi/v2/geodata/ais/openpositions?Xmin=10.09094&Xmax=10.67047&Ymin=63.3989&Ymax=63.58645",
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
