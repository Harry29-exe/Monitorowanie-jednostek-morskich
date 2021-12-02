import React, {useEffect, useState } from 'react';
import {CurrentShipContext, CurrentShipHolder} from '../logic/contexts/CurrentShipContext';
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
    <CurrentShipContext.Provider value={{
      shipHolder: shipContext,
      shipHolderUpdate: updateShips
    }} >
      <ShipMap ships={shipContext.currentShips}/>

    </CurrentShipContext.Provider>
  );
};

export default ShipModule;
