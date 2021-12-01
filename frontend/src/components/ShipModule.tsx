import React, { useState } from 'react';
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

  return (
    <ShipContext.Provider value={{
      shipHolder: shipContext,
      shipHolderUpdate: updateShips
    }} >
      <ShipMapComponent/>
    </ShipContext.Provider>
  );
};

export default ShipModule;

function useEffect(arg0: () => void, arg1: never[]) {
    throw new Error('Function not implemented.');
}
