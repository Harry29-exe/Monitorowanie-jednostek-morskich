import BACKEND_ADDRESS from "../../config/BackendAddress";

export interface LoginResponse {
  status: number;
  username?: string;
  authToken?: string;
}

export default function login(username: string, password: string): Promise<LoginResponse> {
  console.log(username, password);
  return fetch(`${BACKEND_ADDRESS}/login`, {
    body: JSON.stringify({username: username, password: password}),
    headers: {"Content-Type": "application/json"},
    method: "post"
  })
    .then(response => {
      if(response.status <=200 && response.status <300)
        return {status: response.status, username: username, authToken: response.headers.get("Authorization")} as LoginResponse
      else
        return {status: response.status}
    })
    .catch(() => {
      return {status: 400}
    })
}