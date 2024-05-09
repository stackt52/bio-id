import {createContext} from "react";

// Auth context
export const AuthContext = createContext(null);
AuthContext.displayName = "AuthContext";
export const AuthDispatchContext = createContext(null)
AuthDispatchContext.displayName = "AuthDispatchContext";

// Progress context
export const ProgressContext = createContext(null)
ProgressContext.displayName = "ProgressContext";
export const ProgressDispatchContext = createContext(null)
ProgressDispatchContext.displayName = "ProgressDispatchContext";