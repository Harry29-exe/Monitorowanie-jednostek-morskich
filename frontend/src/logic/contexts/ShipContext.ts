import React from "react";
import {CurrentShipInfo} from "../dto/CurrentShipInfo";
import {ShipDTO} from "../dto/ShipDTO";


export class ShipHolder {
  public currentShips: CurrentShipInfo[] = [];
  public selectedShip?: ShipDTO;


}

export interface ShipContextHolder {

  shipHolder: ShipHolder;
  shipHolderUpdate: (shipHolder: ShipHolder) => void;

}

export const ShipContext = React.createContext<ShipContextHolder>({} as ShipContextHolder);