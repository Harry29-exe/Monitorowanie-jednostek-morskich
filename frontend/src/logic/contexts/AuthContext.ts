import {Authentication} from "../../components/user-module/UserModuleWrapper";
import React from "react";

export interface AuthContextHolder {
  auth?: Authentication;
  updateAuth: (auth: Authentication) => any;
}

export const AuthContext = React.createContext<AuthContextHolder>({} as AuthContextHolder)