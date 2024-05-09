import {Button, Container, InputAdornment, TextField} from "@mui/material";
import Box from "@mui/material/Box";
import {Lock, Person} from "@mui/icons-material";
import {grey} from "@mui/material/colors";
import {useContext, useState} from "react";
import {AuthDispatchContext, ProgressDispatchContext} from "../../context/Default";
import {postItem} from "../../util/functions";
import {AuthUserAction, ProgressType} from "../../util/constants";
import {enqueueSnackbar} from "notistack";

export default function LoginForm() {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const dispatch = useContext(AuthDispatchContext)
    const progressStateDispatch = useContext(ProgressDispatchContext)

    const login = (e) => {
        e.preventDefault();

        if (username.length === 0) {
            enqueueSnackbar("Username can not be empty", {variant: "error"})
            return;
        }
        if (password.length === 0) {
            enqueueSnackbar("Password can not be empty", {variant: "error"})
            return;
        }

        progressStateDispatch(
            {
                type: ProgressType.start
            }
        )
        postItem("/auth/users/login", {username, password})
            .then(data => {
                const {username, name, token} = data
                dispatch(
                    {
                        type: AuthUserAction.set,
                        payload: {username, name, token}
                    }
                )
                progressStateDispatch(
                    {
                        type: ProgressType.complete
                    }
                )
            }).catch(err => {
            progressStateDispatch(
                {
                    type: ProgressType.complete
                }
            )
            if (err.response && err.response.status === 401) {
                enqueueSnackbar('Wrong username or password', {variant: "error"})
            }
            console.log(err)
        });
    }

    return (
        <Container sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            minHeight: '100vh'
        }}>
            <form onSubmit={login}
                  style={{width: '100%', display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
                <Box sx={{fontSize: 'h5.fontSize', fontWeight: 'bold', my: 1}}>LOGIN</Box>
                <Box sx={{fontSize: 'body2.fontSize', color: grey[600], my: 1}}>Eyo! Good to see ya ;)</Box>
                <TextField
                    sx={{my: 1}}
                    label="Username"
                    placeholder="Enter your username"
                    variant="filled"
                    required={true}
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <Person/>
                            </InputAdornment>
                        ),
                    }}/>
                <TextField
                    sx={{my: 1}}
                    id="password"
                    label="Password"
                    placeholder="Enter your password"
                    required={true}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    type="password"
                    variant="filled"
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <Lock/>
                            </InputAdornment>
                        ),
                    }}/>

                <Button type="submit" sx={{my: 1}} variant="contained" onClick={login} size="small">LogIn</Button>
            </form>
        </Container>
    )
}