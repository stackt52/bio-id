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
import {AuthContext, AuthDispatchContext, ProgressContext, ProgressDispatchContext} from "./context/Default";
import {authReducer, progressReducer} from "./reducer/Default";
import {lazyLoadRoutes} from "./util/functions";
import {useEffect, useReducer} from "react";
import initRequestObject from "./util/api";
import {getAuthUserData} from "./util/security";
import Welcome from "./component/pages/Welcome";
import ContentShell from "./component/pages/ContentShell";
import {ProgressType} from "./util/constants";
import {SnackbarProvider} from "notistack";

const router = createBrowserRouter(
    createRoutesFromElements(
        <Route>
            <Route path="/" element={<ContentShell/>}>
                <Route index name="Welcome" element={<Welcome/>}/>
                <Route path="login" name="Login" element={lazyLoadRoutes('Login')}/>
                <Route path="dashboard" name="Dashboard" element={lazyLoadRoutes('Dashboard')}>
                    <Route index element={<Home/>}/>
                    <Route path="enrolment" element={<Enrolment/>}/>
                    <Route path="settings" element={<Setting/>}/>
                </Route>
            </Route>
        </Route>
    )
)

function App() {
    const [auth, dispatch] = useReducer(authReducer, {user: getAuthUserData()})
    const [progressState, dispatchProgressState] = useReducer(progressReducer, {state: ProgressType.reset})

    useEffect(() => {
        initRequestObject()
    }, [])

    return (
        <Box sx={{height: '100vh'}} className="App">
            <AuthContext.Provider value={auth}>
                <AuthDispatchContext.Provider value={dispatch}>
                    <ProgressContext.Provider value={progressState}>
                        <ProgressDispatchContext.Provider value={dispatchProgressState}>
                            <SnackbarProvider style={{maxWidth: '400px'}}
                                              preventDuplicate
                                              maxSnack={4}
                                              anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
                                <RouterProvider router={router}/>
                            </SnackbarProvider>
                        </ProgressDispatchContext.Provider>
                    </ProgressContext.Provider>
                </AuthDispatchContext.Provider>
            </AuthContext.Provider>
        </Box>
    );
}

export default App;