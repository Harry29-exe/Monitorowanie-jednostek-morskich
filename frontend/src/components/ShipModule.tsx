import React, {useEffect, useState } from 'react';
import {ShipContext, ShipHolder} from '../logic/contexts/ShipContext';
import ShipMapComponent from "./ShipMapComponent";
import fetchAllShips from "../logic/fetchers/ShipFetcher";

const ShipModule = () => {
  const [shipContext, updateShips] = useState<ShipHolder>(new ShipHolder());

  useEffect(() => {
    fetchAllShips().then(responseBody => {
      let shipCtx = new ShipHolder();
      shipCtx.currentShips = responseBody;
      updateShips(shipCtx);
    })
  }, []);

  console.log(shipContext.currentShips.length)
  return (
    <ShipContext.Provider value={{
      shipHolder: shipContext,
      shipHolderUpdate: updateShips
    }} >
      <ShipMapComponent ships={shipContext.currentShips}/>

    </ShipContext.Provider>
  );
};

export default ShipModule;
