import {ShipWithLocation} from "./ShipWithLocation";
import {ShipDTO} from "./ShipDTO";
import {LocationDTO} from "../LocationDTO";
import BACKEND_ADDRESS from "../../../config/BackendAddress";

export class ShipData implements ShipWithLocation {
  public lastLocation: LocationDTO;
  public shipDTO: ShipDTO;
  public visible: boolean = true;
  public history?: LocationDTO[];
  public isUnderModifications: boolean = false;

  constructor(lastLocation: LocationDTO, shipDTO: ShipDTO) {
    this.lastLocation = lastLocation;
    this.shipDTO = shipDTO;
  }

  public fetchHistory(): Promise<void> {
    let id = this.shipDTO.publicId;
    return fetch(`${BACKEND_ADDRESS}/ships/history/${id}`)
      .then(response => response.json())
      .then(body => this.history = body);
  }

}