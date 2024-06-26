import {useContext, useEffect, useState} from "react";
import {AuthContext, AuthDispatchContext, ProgressContext, ProgressDispatchContext} from "../../context/Default";
import {Outlet, useNavigate} from "react-router-dom";
import * as React from "react";
import Box from "@mui/material/Box";
import {postItem} from "../../util/functions";
import {LinearProgress} from "@mui/material";
import {AuthUserAction, ProgressType} from "../../util/constants";

let __progressUpdaterInterval = null

export default function ContentShell() {
    const auth = useContext(AuthContext)
    const dispatch = useContext(AuthDispatchContext)
    const navigate = useNavigate()
    const [progress, setProgress] = useState(0)
    const [hide, setHide] = useState(false)
    const progressState = useContext(ProgressContext)
    const progressStateDispatch = useContext(ProgressDispatchContext)

    useEffect(() => {
        const timeout = setTimeout(() => {
            if (auth.user) {
                const {username, name, token} = auth.user;
                postItem('/auth/token/validate', {username, name, token}).then(() => {
                    navigate("/dashboard")
                }).catch(() => {
                    console.warn('Auth token has expired!')
                    // invalidate cached token when expired
                    if (dispatch)
                        dispatch(
                            {
                                type: AuthUserAction.reset
                            }
                        )
                })
            } else
                navigate("/login")
        }, 3000);

        return () => {
            clearTimeout(timeout)
        }
    }, [auth, dispatch, navigate])

    // Update the progress bar based on the status of the pressState
    useEffect(() => {
        switch (progressState.state) {
            case ProgressType.start:
                setHide(false)
                if (progress === 0) {
                    // Gradually update the progress bar from 0 to <= 75
                    // between the intervals of 250 milliseconds
                    __progressUpdaterInterval = setInterval(() => {
                        if (progress <= 75)
                            setProgress(progress + 5)
                    }, 250)
                }
                break
            case ProgressType.complete:
                if (__progressUpdaterInterval != null) {
                    clearInterval(__progressUpdaterInterval)
                    setProgress(100)
                    setTimeout(() => {
                        if (progressStateDispatch)
                            progressStateDispatch(
                                {
                                    type: ProgressType.reset,
                                }
                            )
                    }, 1500)
                    __progressUpdaterInterval = null
                }
                break
            default:
                setHide(true)
                setProgress(0)
                break
        }

    }, [progressState, progress, progressStateDispatch])


    return (
        <Box sx={{height: '100vh'}} className="App">
            <LinearProgress hidden={hide} variant="determinate" value={progress} sx={{
                position: 'fixed',
                width: '100%',
                top: 0,
                left: 0,
                zIndex: 9999,
                backgroundColor: 'rgba(255,255,255,.2)'
            }}/>
            <Outlet/>
        </Box>
    )
}