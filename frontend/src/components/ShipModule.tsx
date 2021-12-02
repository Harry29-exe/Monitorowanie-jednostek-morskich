import React, {useEffect, useState } from 'react';
import { CurrentShipHolder} from '../logic/contexts/CurrentShipContext';
import ShipMap from "./ShipMap";
import fetchAllShips from "../logic/fetchers/ShipFetcher";

const ShipModule = () => {
  const [shipContext, updateShips] = useState<CurrentShipHolder>(new CurrentShipHolder());

  useEffect(() => {
    fetchAllShips().then(responseBody => {
      let shipCtx = new CurrentShipHolder();
      shipCtx.currentShips = responseBody;
      updateShips(shipCtx);
    })
  }, []);

  console.log(shipContext.currentShips.length)
  return (
    <ShipMap ships={shipContext.currentShips} traces={[]}/>
  );
};

export default ShipModule;
