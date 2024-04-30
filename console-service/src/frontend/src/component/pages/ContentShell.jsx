import {useContext, useEffect} from "react";
import {AuthContext, AuthDispatchContext} from "../../context/Default";
import {Outlet, useNavigate} from "react-router-dom";
import * as React from "react";
import Box from "@mui/material/Box";
import {postItem} from "../../util/functions";

export default function ContentShell() {
    const auth = useContext(AuthContext)
    const dispatch = useContext(AuthDispatchContext)
    const navigate = useNavigate()

    useEffect(() => {
        const timeout = setTimeout(() => {
            if (auth.user) {
                const {username, name, token} = auth.user;
                postItem('/auth/token/validate', {username, name, token}).then(data => {
                    navigate("/dashboard")
                }).catch(e => {
                    console.warn('Auth token has expired!')
                    // invalidate cached token when expired
                    dispatch(
                        {
                            type: "RESET_USER"
                        }
                    )
                })
            } else
                navigate("/login")
        }, 3000);

        return () => {
            clearTimeout(timeout)
        }
    }, [auth])


    return (
        <Box sx={{height: '100vh'}} className="App">
            <Outlet/>
        </Box>
    )
}