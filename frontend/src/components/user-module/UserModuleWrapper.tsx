import React, {useContext, useEffect, useState} from 'react';
import LoginModal from "./LoginModal";
import ShipMap from "../ShipMap";
import fetchTrackedShips from "../../logic/fetchers/TrackedShips";
import {CurrentShipInfo} from "../../logic/dto/ships/CurrentShipInfo";
import ShipExplorer from "./ShipExplorer";
import {Center, HStack, useBoolean} from "@chakra-ui/react";
import {ShipData} from "../../logic/dto/ships/ShipData";
import fetchShipHistory from "../../logic/fetchers/FetchShipHistory";
import {LocationDTO} from "../../logic/dto/LocationDTO";
import {AuthContext} from "../../logic/contexts/AuthContext";

export interface Authentication {
  username: string;
  authToken: string;
}

function getShip(id: string, shipsData: ShipData[]): ShipData | null {
  let ships = shipsData.filter(f => f.shipDTO.publicId === id);
  if(ships.length === 0) return null;
  return ships[0];
}

export enum OperationStatus {
  OK,
  FAIL
}

const UserModuleWrapper = () => {
  const [shipsData, updateShips] = useState<ShipData[]>([]);
  const [shipsFetchInit, fetchShips] = useBoolean(false);
  const authContext = useContext(AuthContext);
  const auth = authContext.auth;

  const updateShipsData = () => updateShips(shipsData.map(s => s))

  const showShip = (shipId: string): Promise<OperationStatus> => {
    let ships = shipsData.filter(f => f.shipDTO.publicId === shipId);
    if(ships.length > 0) {
      if (!!auth) {
        return fetchShipHistory(auth.authToken, ships[0].shipDTO.publicId)
          .then(history => {
            ships[0].history = history.history;
            ships[0].displayHistory = true;
            updateShipsData();
          }).then(() => OperationStatus.OK);
      }
      return Promise.resolve(OperationStatus.FAIL);
    } else {
      return Promise.resolve(OperationStatus.FAIL);
    }
  }

  const hideShip = (shipId: string): Promise<OperationStatus> => {
    let ship = getShip(shipId, shipsData);
    if(!!ship) {
      ship.displayHistory = false;
      updateShipsData();
      return Promise.resolve(OperationStatus.OK);
    }
    return Promise.resolve(OperationStatus.FAIL);
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
  }, [auth, shipsFetchInit]);

  let traces: LocationDTO[][] = shipsData
    .filter(s => s.displayHistory && !!s.history)
    .map(s => s.history) as LocationDTO[][];


  return (
    <HStack m={0} p={0} h="100%" w="100%" pos="relative">
      {!auth?
        <Center w="100%" h="100%">
          <LoginModal onLogin={authContext.updateAuth}/>
        </Center>
        :
        <>
          <ShipExplorer ships={shipsData.map(s => s.shipDTO)}
                        show={showShip}
                        hide={hideShip}
                        initShipFetch={fetchShips.toggle}
          />

          <ShipMap ships={shipsData.map(s => CurrentShipInfo.from(s))}
                   traces={traces? traces: []}/>

        </>
      }
    </HStack>
  );
};

export default UserModuleWrapper;