import {CurrentLocation} from "./CurrentLocation";
import {ShipDTO} from "./ShipDTO";

export class CurrentShipInfo implements ShipDTO{
  public type: string;
  public destination: string;
  public currentLocation: CurrentLocation;
  public mmsi: number;
  public name: string;


  constructor(type: string, destination: string, currentLocation: CurrentLocation, mmsi: number, name: string) {
    this.type = type;
    this.destination = destination;
    this.currentLocation = currentLocation;
    this.mmsi = mmsi;
    this.name = name;
  }
}