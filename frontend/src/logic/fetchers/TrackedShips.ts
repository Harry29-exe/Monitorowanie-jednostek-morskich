import {ShipWithLocation} from "../dto/ships/ShipWithLocation";
import BACKEND_ADDRESS from "../../config/BackendAddress";

export default function fetchTrackedShips(authToken: string): Promise<ShipWithLocation[]> {
  return fetch(`${BACKEND_ADDRESS}/ships/tracked-ships`, {
    headers: {"Authorization": authToken}
  }).then(response => response.json())
    .catch(reason => console.log(reason));
}