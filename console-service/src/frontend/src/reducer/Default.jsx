import {clearAuthUserData, saveAuthUser} from "../util/security";

export function authReducer(state, action) {
    switch (action.type) {
        case 'SET_USER':
            saveAuthUser(action.payload)
            return {...state, user: action.payload};
        case 'RESET_USER':
            clearAuthUserData()
            return {...state, user: undefined};
        case 'UPDATE_USER':
            saveAuthUser(action.payload)
            return {...state, user: action.payload};
        default:
            return state;
    }
}