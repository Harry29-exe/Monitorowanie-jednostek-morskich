import {ShipWithHistory} from "../dto/ships/ShipWithHistory";
import BACKEND_ADDRESS from "../../config/BackendAddress";

export default function fetchShipHistory(auth: string, id: number) :Promise<ShipWithHistory> {
  return fetch(`${BACKEND_ADDRESS}/ships/history/${id}`, {
    headers: {"Authorization": auth},
    method: "post"
  }).then(response => response.json());
}