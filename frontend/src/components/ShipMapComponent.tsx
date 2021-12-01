import React, {useEffect, useState } from 'react';
import {DivIcon, LayersControlEvent, LeafletEvent, LeafletMouseEvent, LocationEvent, Map as LeafletMap} from "leaflet";
import {MapContainer, Marker, Popup, TileLayer } from 'react-leaflet';
import {Box, VStack } from '@chakra-ui/react';
import fetchAllShips from "../logic/fetchers/ShipFetcher";
import {CurrentShipInfo} from "../logic/dto/CurrentShipInfo";
import {shipMarkerReducer} from "../logic/ShipMarkerReducer";
import {Area} from "../logic/dto/Area";
import {ShipMarker} from "../logic/dto/ShipMarker";

interface Coords {
  x: number;
  y: number;
}

const ShipMapComponent = () => {
  const [map, setMap] = useState<LeafletMap>();
  const [coords, setCoords] = useState<{from: Coords, to: Coords}>();
  const [ships, setShips] = useState<CurrentShipInfo[]>();

  useEffect(() => {
    fetchAllShips().then(responseBody => setShips(responseBody))
  }, []);

  useEffect(() => {
    if(!map) return;
    const updateCoords = (e: LeafletEvent) => {
      let bounds = map.getBounds();
      let topLeft = bounds.getNorthWest();
      let bottomRight = bounds.getSouthEast();
      setCoords({
        from: {x: topLeft.lat, y: topLeft.lng},
        to: {x: bottomRight.lat, y: bottomRight.lng}
      });
    }
    map.addEventListener("moveend", updateCoords);
    return () => {
      if(!map) return;
      map.removeEventListener("moveend", updateCoords);
    }
  }, [map]);

  let markers: ShipMarker[] = [];
  if(!!ships && !!coords) {
    markers = shipMarkerReducer(ships, new Area(coords.from.x, coords.from.y, coords.to.x, coords.to.y))
  }

  return (
    <VStack>
      <MapContainer
        center={[59.9138, 10.7522]} zoom={10}
        scrollWheelZoom={true} style={{height: "500px", width: "800px"}}
        whenCreated={map => setMap(map)}
      >

        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        {markers.map(m => (
          <Marker position={[m.y, m.x]} key={`${m.y}, ${m.x}`}
                  icon={new DivIcon({html: createMarker(m.ships.length), iconSize: [0,0]})}
          >
            <Popup>
              {
                m.toMarkerString()
              }
            </Popup>
          </Marker>
        ))}


      </MapContainer>
      <Box>{`from: x: ${coords?.from.x}, y: ${coords?.from.y}`}</Box>
      <Box>{`to: x: ${coords?.to.x}, y: ${coords?.to.y}`}</Box>
    </VStack>
    );
};

function createMarker(shipsCount: number): HTMLElement {
  let size = 12;
  let el = document.createElement("div") as HTMLDivElement;
  el.style["borderRadius"] = "100%";
  el.style["marginLeft"] = `${-size/2}px`;
  el.style["marginTop"] = `${-size/2}px`;
  el.style["width"] = `${size}px`;
  el.style["height"] = `${size}px`;
  el.style["background"] = shipsCount === 1? "blue":
    shipsCount > 20? "DarkViolet": shipsCount > 5? "yellow": "green";
  el.style["border"] = "1px solid black"

  return el;
}

export default ShipMapComponent;