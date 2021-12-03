import React, {useEffect, useRef, useState} from 'react';
import {DivIcon, Map as LeafletMap} from "leaflet";
import {MapContainer, Marker, Polyline, Popup, TileLayer} from 'react-leaflet';
import {Box, Button, Center, HStack, NumberInput, NumberInputField, useBoolean, VStack} from '@chakra-ui/react';
import {CurrentShipInfo} from "../logic/dto/ships/CurrentShipInfo";
import {shipMarkerReducer, shipMarkerReducerSmallDistance} from "../logic/map-related/ShipMarkerReducer";
import {Area} from "../logic/dto/Area";
import {ShipMarkerDTO} from "../logic/dto/ShipMarkerDTO";
import ShipMarker from './ShipMarker';
import {LocationDTO} from "../logic/dto/LocationDTO";

interface Coords {
  x: number;
  y: number;
}

const ShipMap = (props: {ships: CurrentShipInfo[], traces: LocationDTO[][]}) => {
  const [map, setMap] = useState<LeafletMap>();
  const [markers, setMarkers] = useState<ShipMarkerDTO[]>([]);
  const [mapW, setMapW] = useState<number>(1200);
  const [mapH, setMapH] = useState<number>(600);

  useEffect(() => {
    if(!map) return;
    const updateMarkers = () => {
      let zoom = map.getZoom();
      let bounds = map.getBounds();
      let topLeft = bounds.getNorthWest();
      let bottomRight = bounds.getSouthEast();
      let area = new Area(topLeft.lng, topLeft.lat, bottomRight.lng, bottomRight.lat);

      if(zoom >= 10) {
        setMarkers(shipMarkerReducerSmallDistance(props.ships, area))
      } else {
        setMarkers(shipMarkerReducer(props.ships, area));
      }
    }
    map.addEventListener("zoomend", updateMarkers);
    return () => {
      if(!map) return;
      map.removeEventListener("zoomend", updateMarkers);
    }
  }, [map, props.ships]);

  return (
    <Center zIndex={1} w="100%" h="100%" p={10}>
      <Box h={mapH + "px"} w={mapW + "px"}>
        <MapContainer
          center={[59.9138, 10.7522]} zoom={10}
          scrollWheelZoom={true} style={{height: "100%", width: "100%"}}
                      // style={{height: `${mapH}px`, width: `${mapW}px`}}
          whenCreated={map => setMap(map)}
        >

          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />

          {!!props.traces && props.traces.length > 0 &&
            props.traces.map(t => (
              <Polyline pathOptions={{color: "blue"}} positions={t.map(location => [location.y, location.x])}/>
            ))
          }

          {markers.map(m => (
            <ShipMarker marker={m} key={m.toMarkerString()}/>
          ))}


        </MapContainer>
      </Box>



      <VStack pos="absolute" bottom={0} right={0} minW={"300px"} minH={"100px"} borderTopLeftRadius={"md"} bg={"teal.600"} p={4}>
        <HStack>
          <Box>Map width</Box>
          <NumberInput onChange={(value:string, num: number) => {
            console.log(value)
            setMapW(num)
          }}>
            <NumberInputField />
          </NumberInput>
        </HStack>

        <Button onClick={() => {}}>Change map size</Button>
      </VStack>
    </Center>

    );
};

export default ShipMap;