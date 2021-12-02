import React from 'react';
import {DivIcon} from "leaflet";
import {createMarker} from "../logic/map-related/CreateMarker";
import {Marker, Popup} from "react-leaflet";
import {Box, VStack} from "@chakra-ui/react";
import {ShipMarkerDTO} from "../logic/dto/ShipMarkerDTO";

const ShipMarker = (props: {marker: ShipMarkerDTO}) => {
  const m = props.marker;
  return (
    <Marker position={[m.y, m.x]}
            icon={new DivIcon({html: createMarker(m.ships.length), iconSize: [0,0]})}
    >
      <Popup>
        <VStack maxH={"100px"} overflowY={"auto"}>
          <Box fontSize={"14px"}>Ships in region:</Box>
          <Box w="100%" borderBottom={"2px solid black"}/>
          {m.ships.map(s => (
            <div key={s.mmsi}>
              <Box>{s.name}</Box>
              <Box>{s.mmsi}</Box>
              <Box w="100%" borderBottom={"2px solid black"}/>
            </div>
          ))
          }
        </VStack>
      </Popup>
    </Marker>
  );
};

export default ShipMarker;