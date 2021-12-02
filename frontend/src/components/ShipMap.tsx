import React, {useEffect, useState} from 'react';
import {DivIcon, Map as LeafletMap} from "leaflet";
import {MapContainer, Marker, Popup, TileLayer} from 'react-leaflet';
import {Box, Center, VStack} from '@chakra-ui/react';
import {CurrentShipInfo} from "../logic/dto/ships/CurrentShipInfo";
import {shipMarkerReducer} from "../logic/map-related/ShipMarkerReducer";
import {Area} from "../logic/dto/Area";
import {ShipMarkerDTO} from "../logic/dto/ShipMarkerDTO";
import {createMarker} from "../logic/map-related/CreateMarker";
import ShipMarker from './ShipMarker';

interface Coords {
  x: number;
  y: number;
}

const ShipMap = (props: {ships: CurrentShipInfo[]}) => {
  const [map, setMap] = useState<LeafletMap>();
  const [markers, setMarkers] = useState<ShipMarkerDTO[]>([]);

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
    <Center zIndex={1} w="100%" h="100%" p={10}>
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
          <ShipMarker marker={m} key={m.toMarkerString()}/>
        ))}


      </MapContainer>
    </Center>
    );
};

export default ShipMap;