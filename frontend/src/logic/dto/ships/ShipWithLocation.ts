import {ShipDTO} from "./ShipDTO";
import {LocationDTO} from "../LocationDTO";

export interface ShipWithLocation {
  shipDTO: ShipDTO;
  lastLocation: LocationDTO
}
