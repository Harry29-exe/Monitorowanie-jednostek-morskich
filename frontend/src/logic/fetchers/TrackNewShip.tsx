import BACKEND_ADDRESS from "../../config/BackendAddress";

export default function trackNewShip(auth: string, mmsi: number): Promise<number> {
  return fetch(`${BACKEND_ADDRESS}/ships/tracking/${mmsi}`, {
    method: "post",
    headers: {"Authorization": auth}
  }).then(response => response.status);
}