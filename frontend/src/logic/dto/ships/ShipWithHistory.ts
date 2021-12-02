import {ShipDTO} from "./ShipDTO";
import {LocationDTO} from "../LocationDTO";

export interface ShipWithHistory {
  ship: ShipDTO,
  history: LocationDTO[]
}