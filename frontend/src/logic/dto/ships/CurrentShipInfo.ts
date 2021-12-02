import {LocationDTO} from "../LocationDTO";
import {ShipIdDTO} from "./ShipIdDTO";
import {ShipDTO} from "./ShipDTO";
import {ShipWithLocation} from "./ShipWithLocation";

export class CurrentShipInfo implements ShipIdDTO {
  public type: string;
  public destination?: string;
  public currentLocation: LocationDTO;
  public mmsi: number;
  public name: string;


  constructor(type: string, currentLocation: LocationDTO, mmsi: number, name: string,  destination?: string) {
    this.type = type;
    this.destination = destination;
    this.currentLocation = currentLocation;
    this.mmsi = mmsi;
    this.name = name;
  }

  public static from(ship: ShipWithLocation): CurrentShipInfo {
    let s = ship.shipDTO;
    return new CurrentShipInfo(s.shipType, ship.lastLocation, s.mmsi, s.name, undefined);
  }

}