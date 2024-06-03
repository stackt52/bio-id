import {lazy, Suspense} from "react";
import axios from "axios";

export function lazyLoadRoutes(componentName, props = {}) {
    const LazyElement = lazy(() => import(`../component/pages/${componentName}`));
    return (
        <Suspense fallback="Loading...">
            <LazyElement {...props}/>
        </Suspense>
    );
}

/**
 * Sends a GET http request to the REST backend services
 * @param url relative url to the endpoint
 * @returns {Promise<>} a Promise with value(s) when resolved, else the error
 * when rejected
 */
export function getItem(url) {
    return new Promise((resolve, reject) => {
        axios.get(url).then(response => {
            resolve(response.data);
        }).catch(error => {
            reject(error);
        })
    })
}

/**
 * Sends a POST http request to the REST backend services.
 * @param url relative url to the endpoint
 * @param data sent as request body
 * @param config Axios request configurations
 * @returns {Promise<unknown>} a Promise with value(s) when resolved, else the error
 * when rejected
 */
export function postItem(url, data, config = {}) {
    return new Promise((resolve, reject) => {
        axios.post(url, data, config).then(response => {
            resolve(response.data);
        }).catch(error => {
            reject(error);
        })
    })
}

/**
 * Sends a PUT http request to the REST backend services.
 * @param url relative url to the endpoint
 * @param data sent as request body
 * @returns {Promise<unknown>} a Promise with value(s) when resolved, else the error
 * when rejected
 */
export function updateItem(url, data) {
    return new Promise((resolve, reject) => {
        axios.put(url, data).then(response => {
            resolve(response.data);
        }).catch(error => {
            reject(error);
        })
    })
}

/**
 * Sends a DELETE http request to the REST backend services
 * @param url relative url to the endpoint
 * @returns {Promise<>} a Promise with value(s) when resolved, else the error
 * when rejected
 */
export function deleteItem(url) {
    return new Promise((resolve, reject) => {
        axios.delete(url).then(response => {
            resolve(response.data);
        }).catch(error => {
            reject(error);
        })
    })
}