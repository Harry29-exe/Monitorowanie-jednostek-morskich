import BACKEND_ADDRESS from "../config/BackendAddress";

export default function fetchAllShips (): Promise<CurrentShipInfo[]> {
  return fetch(`${BACKEND_ADDRESS}/ships/all`)
    .then(response => response.json())
}

export interface CurrentLocation {
  x: number;
  y: number;
  time: Date;
}

export interface CurrentShipInfo {
  mmsi: number;
  name: string;
  type: string;
  destination: string;
  currentLocation: CurrentLocation;

}