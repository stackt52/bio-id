import Dashboard from "./components/pages/Dashboard";
import Box from "@mui/material/Box";
import Welcome from "./components/pages/Welcome";
import SignIn from "./components/pages/SignIn";

import {
    createBrowserRouter,
    createRoutesFromElements,
    Route,
    RouterProvider,
} from "react-router-dom";
import Home from "./components/pages/Home";
import Enrolment from "./components/pages/Enrolment";
import Setting from "./components/pages/Setting";

const router = createBrowserRouter(
    createRoutesFromElements(
        <Route>
            <Route index element={<Welcome/>}/>
            <Route path="signin" element={<SignIn/>}/>
            <Route path="dashboard" element={<Dashboard/>}>
                <Route index element={<Home/>}/>
                <Route path="enrolment" element={<Enrolment/>}/>
                <Route path="settings" element={<Setting/>} />
            </Route>
        </Route>
    )
)

function App() {
    return (
        <Box sx={{height: '100vh'}} className="App">
            <RouterProvider router={router}/>
        </Box>
    );
}

export default App;