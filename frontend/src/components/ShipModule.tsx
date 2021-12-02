import React, {useEffect, useState } from 'react';
import ShipMap from "./ShipMap";
import fetchAllShips from "../logic/fetchers/ShipFetcher";
import {CurrentShipInfo} from "../logic/dto/ships/CurrentShipInfo";

const ShipModule = () => {
  const [shipContext, updateShips] = useState<CurrentShipInfo[]>([]);

  useEffect(() => {
    fetchAllShips().then(responseBody => {
      updateShips(responseBody);
    })
  }, []);

  return (
    <ShipMap ships={shipContext} traces={[]}/>
  );
};

export default ShipModule;
