import BACKEND_ADDRESS from "../../config/BackendAddress";

export default function stopTrackingShip(auth: string, publicId: string) {
  fetch(`${BACKEND_ADDRESS}/ships/tracking/${publicId}`, {
    method: "delete",
    headers: {"Authorization": auth}
  });
}