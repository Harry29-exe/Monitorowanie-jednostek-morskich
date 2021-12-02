import React, {useEffect, useState} from 'react';
import LoginModal from "./LoginModal";
import ShipMap from "../ShipMap";
import fetchTrackedShips from "../../logic/fetchers/TrackedShips";
import {ShipWithLocation} from "../../logic/dto/ships/ShipWithLocation";
import {CurrentShipInfo} from "../../logic/dto/ships/CurrentShipInfo";
import ShipExplorer from "./ShipExplorer";
import {Center, HStack} from "@chakra-ui/react";
import {ShipData} from "../../logic/dto/ships/ShipData";
import RegisterModal from "./RegisterModal";
import fetchShipHistory from "../../logic/fetchers/FetchShipHistory";
import {LocationDTO} from "../../logic/dto/LocationDTO";

export interface Authentication {
  username: string;
  authToken: string;
}

enum Modes {
  VIEW_ALL,
  VIEW_SINGLE
}

function getShip(id: string, shipsData: ShipData[]): ShipData | null {
  let ships = shipsData.filter(f => f.shipDTO.publicId === id);
  if(ships.length === 0) return null;
  return ships[0];
}

const UserModuleWrapper = () => {
  const [auth, setAuth] = useState<Authentication | null>(null);
  const [shipsData, updateShips] = useState<ShipData[]>([]);
  const [activeShip, setActiveShip] = useState<string>();

  const updateShipsData = () => updateShips(shipsData.map(s => s))

  const setModeToViewSingle = (shipId: string) => {
    let ships = shipsData.filter(f => f.shipDTO.publicId === shipId);
    if(ships.length > 0) {
      if(ships[0].history) {
        setActiveShip(ships[0].shipDTO.publicId);
      } else if (!!auth) {
        fetchShipHistory(auth.authToken, ships[0].shipDTO.publicId)
          .then(history => {
            ships[0].history = history.history;
            updateShipsData();
            setActiveShip(ships[0].shipDTO.publicId);
          })
      }
    } else {
      throw new Error();
    }
  }

  useEffect(() => {
    if(!auth) return;
    fetchTrackedShips(auth.authToken)
      .then(body => {
        if(!body) {
          console.log("Body is broken");
          return;
        }
        updateShips(body.map(s => new ShipData(s.lastLocation, s.shipDTO)));
      });
  }, [auth]);

  let traces: any;
  if(activeShip) {
    let ship = getShip(activeShip, shipsData);
    if(!ship) traces = null;
    else traces = [ship.history];
  } else {
    traces = null;
  }

  // debugger;
  return (
    <HStack m={0} p={0} h="100%" w="100%" pos="relative">
      {!auth?
        <Center w="100%" h="100%">
          <LoginModal onLogin={setAuth}/>
        </Center>
        :
        <>
          <ShipExplorer ships={shipsData.map(s => s.shipDTO)}
                        setSingleView={setModeToViewSingle}
          />

          <ShipMap ships={shipsData.map(s => CurrentShipInfo.from(s))} traces={traces? traces: []}/>


        </>
      }
    </HStack>
  );
};

export default UserModuleWrapper;