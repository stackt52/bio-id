import Box from "@mui/material/Box";
import {
    createBrowserRouter,
    createRoutesFromElements,
    Route,
    RouterProvider,
} from "react-router-dom";
import Home from "./component/pages/Home";
import Enrolment from "./component/pages/Enrolment";
import Setting from "./component/pages/Setting";
import {AuthContext, AuthDispatchContext} from "./context/Default";
import {authReducer} from "./reducer/Default";
import {lazyLoadRoutes} from "./util/functions";
import {useEffect, useReducer} from "react";
import initRequestObject from "./util/api";
import {getAuthUserData} from "./util/security";

const router = createBrowserRouter(
    createRoutesFromElements(
        <Route>
            <Route index name="Welcome" element={lazyLoadRoutes('Welcome')} />
            <Route path="login" name="Login" element={lazyLoadRoutes('Login')}/>
            <Route path="dashboard" name="Dashboard" element={lazyLoadRoutes('Dashboard')}>
                <Route index element={<Home/>}/>
                <Route path="enrolment" element={<Enrolment/>}/>
                <Route path="settings" element={<Setting/>}/>
            </Route>
        </Route>
    )
)

function App() {
    const [auth, dispatch] = useReducer(authReducer, {user: getAuthUserData()})
    useEffect(() => {
        initRequestObject()
    },[])

    return (
        <Box sx={{height: '100vh'}} className="App">
            <AuthContext.Provider value={auth}>
                <AuthDispatchContext.Provider value={dispatch}>
                    <RouterProvider router={router}/>
                </AuthDispatchContext.Provider>
            </AuthContext.Provider>
        </Box>
    );
}

export default App;