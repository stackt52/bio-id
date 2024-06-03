import axios from "axios";
import {getAuthToken} from "./security";

export default function initRequestObject() {
    axios.defaults.baseURL = 'http://127.0.0.1:8081/console';
    axios.defaults.headers.post['Content-Type'] = 'application/json';

    const token = getAuthToken();
    if (token)
        axios.defaults.headers.common['Authorization'] = 'Bearer '+ token

}