import BACKEND_ADDRESS from "../../config/BackendAddress";
import {CurrentShipInfo} from "../dto/CurrentShipInfo";

export default function fetchAllShips (): Promise<CurrentShipInfo[]> {
  return fetch(`${BACKEND_ADDRESS}/ships/all`)
    .then(response => response.json())
}

