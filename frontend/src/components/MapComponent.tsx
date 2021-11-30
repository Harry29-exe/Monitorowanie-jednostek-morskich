import React, {useEffect, useState } from 'react';
import {LayersControlEvent, LeafletEvent, LeafletMouseEvent, LocationEvent, Map as LeafletMap} from "leaflet";
import {MapContainer, Marker, Popup, TileLayer } from 'react-leaflet';
import {Box, VStack } from '@chakra-ui/react';

interface Coords {
  x: number;
  y: number;
}

const MapComponent = () => {
  const [map, setMap] = useState<LeafletMap>();
  const [coords, setCoords] = useState<{from: Coords, to: Coords}>();


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

        <Marker position={[51.505, -0.09]}>
          <Popup>
            A pretty CSS3 popup. <br /> Easily customizable.
          </Popup>
        </Marker>

      </MapContainer>
      <Box>{`from: x: ${coords?.from.x}, y: ${coords?.from.y}`}</Box>
      <Box>{`to: x: ${coords?.to.x}, y: ${coords?.to.y}`}</Box>
    </VStack>
    );
};

export default MapComponent;