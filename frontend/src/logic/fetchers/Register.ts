import BACKEND_ADDRESS from "../../config/BackendAddress";

export default function register(username: string, password: string): Promise<number> {
  return fetch(`${BACKEND_ADDRESS}/register`, {
      body: JSON.stringify({username: username, password: password}),
      headers: {"Content-Type": "application/json"},
      method: "post"
    }).then(response => response.status)
      .catch(() => 400);
}