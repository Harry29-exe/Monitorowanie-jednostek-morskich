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

export interface Authentication {
  username: string;
  authToken: string;
}

const UserModuleWrapper = () => {
  const [auth, setAuth] = useState<Authentication | null>(null);
  const [shipsData, updateShips] = useState<ShipData[]>([]);

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

  return (
    <HStack m={0} p={0} h="100%" w="100%" pos="relative">
      {!auth?
        <Center w="100%" h="100%">
          <LoginModal onLogin={setAuth}/>
        </Center>
        :
        <>
          <ShipExplorer ships={shipsData.map(s => s.shipDTO)}/>
          <ShipMap ships={shipsData.map(s => CurrentShipInfo.from(s))}/>
        </>
      }
    </HStack>
  );
};

export default UserModuleWrapper;