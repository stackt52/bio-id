const AUTH_STORAGE_KEY = 'AUTH_USER_DETAILS';

export function saveAuthUser(data) {
    localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(data));
}

export function getAuthUserData() {
    const strObj = localStorage.getItem(AUTH_STORAGE_KEY)
    if (strObj)
        return JSON.parse(strObj);
    return null
}

export function clearAuthUserData() {
    localStorage.removeItem(AUTH_STORAGE_KEY);
}

export function getAuthToken() {
    const data = getAuthUserData()
    if (data)
        return data.token;
    return null
}