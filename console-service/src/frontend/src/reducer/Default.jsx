import {clearAuthUserData, saveAuthUser} from "../util/security";
import {AuthUserAction, ProgressType} from "../util/constants";

export function authReducer(state, action) {
    switch (action.type) {
        case AuthUserAction.set:
            saveAuthUser(action.payload)
            return {...state, user: action.payload};
        case AuthUserAction.reset:
            clearAuthUserData()
            return {...state, user: undefined};
        case AuthUserAction.update:
            saveAuthUser(action.payload)
            return {...state, user: action.payload};
        default:
            return state;
    }
}

export function progressReducer(state, action) {
    switch (action.type) {
        case ProgressType.start:
            return {...state, state: action.type};
        case ProgressType.complete:
            return {...state, state: action.type};
        case ProgressType.reset:
            return {...state, state: action.type};
        default:
            return state
    }
}