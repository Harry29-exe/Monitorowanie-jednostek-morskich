import React, {useEffect, useState } from 'react';
import {DivIcon, LayersControlEvent, LeafletEvent, LeafletMouseEvent, LocationEvent, Map as LeafletMap} from "leaflet";
import {MapContainer, Marker, Popup, TileLayer } from 'react-leaflet';
import {Box, Divider, VStack } from '@chakra-ui/react';
import {CurrentShipInfo} from "../logic/dto/CurrentShipInfo";
import {shipMarkerReducer} from "../logic/ShipMarkerReducer";
import {Area} from "../logic/dto/Area";
import {ShipMarker} from "../logic/dto/ShipMarker";

interface Coords {
  x: number;
  y: number;
}

const ShipMapComponent = (props: {ships: CurrentShipInfo []}) => {
  const [map, setMap] = useState<LeafletMap>();
  const [markers, setMarkers] = useState<ShipMarker[]>([]);

  useEffect(() => {
    if(!map) return;
    const updateMarkers = () => {
      let bounds = map.getBounds();
      let topLeft = bounds.getNorthWest();
      let bottomRight = bounds.getSouthEast();
      let currentMarkers = shipMarkerReducer(props.ships, new Area(topLeft.lat, topLeft.lng, bottomRight.lat, bottomRight.lng));
      setMarkers(currentMarkers);
    }
    map.addEventListener("zoomend", updateMarkers);
    return () => {
      if(!map) return;
      map.removeEventListener("zoomend", updateMarkers);
    }
  }, [map, props.ships]);

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
          <Marker position={[m.y, m.x]} key={m.toMarkerString()}
                  icon={new DivIcon({html: createMarker(m.ships.length), iconSize: [0,0]})}
          >
            <Popup>
              <VStack maxH={"100px"} overflowY={"auto"}>
                <Box fontSize={"14px"}>Ships in region:</Box>
                <Box w="100%" borderBottom={"2px solid black"}/>
                {m.ships.map(s => (
                  <>
                    <Box>{s.name}</Box>
                    <Box>{s.mmsi}</Box>
                    <Box w="100%" borderBottom={"2px solid black"}/>
                  </>
                ))
                }
              </VStack>
            </Popup>
          </Marker>
        ))}


      </MapContainer>
    </VStack>
    );
};

function createMarker(shipsCount: number): HTMLElement {
  let size = shipsCount === 1? 10: shipsCount > 20? 16: shipsCount > 5? 14: 12;
  let el = document.createElement("div") as HTMLDivElement;
  el.style["borderRadius"] = "100%";
  el.style["marginLeft"] = `${-size/2}px`;
  el.style["marginTop"] = `${-size/2}px`;
  el.style["width"] = `${size}px`;
  el.style["height"] = `${size}px`;
  el.style["background"] = shipsCount === 1? "blue":
    shipsCount > 20? "rgb(115, 50, 168, 0.6)":
      shipsCount > 5? "rgb(201, 209, 48, 0.8)": "rgb(48, 209, 61, 0.95)";
  el.style["border"] = "1px solid black"

  return el;
}

export default ShipMapComponent;