import {createContext} from "react";

export const AuthContext = createContext(null);
AuthContext.displayName = "AuthContext";
export const AuthDispatchContext = createContext(null)
AuthDispatchContext.displayName = "AuthDispatchContext";