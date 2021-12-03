import BACKEND_ADDRESS from "../../config/BackendAddress";

export default function deleteShip(auth: string, shipId: string): Promise<boolean> {
  return fetch(`${BACKEND_ADDRESS}/ships/${shipId}`, {
    method: "delete",
    headers: {"Authorization": auth}
  }).then(response => response.status < 300 && response.status >= 200);
}