import React from "react";
import {CurrentShipInfo} from "../dto/ships/CurrentShipInfo";
import {ShipIdDTO} from "../dto/ships/ShipIdDTO";


export class CurrentShipHolder {
  public currentShips: CurrentShipInfo[] = [];
  public selectedShip?: ShipIdDTO;

  public clone(): CurrentShipHolder {
    let clone = new CurrentShipHolder();
    clone.selectedShip = this.selectedShip;
    clone.currentShips = this.currentShips;
    return clone;
  }

}

export interface CurrentShipContextHolder {

  shipHolder: CurrentShipHolder;
  shipHolderUpdate: (shipHolder: CurrentShipHolder) => void;

}

export const CurrentShipContext = React.createContext<CurrentShipContextHolder>({} as CurrentShipContextHolder);