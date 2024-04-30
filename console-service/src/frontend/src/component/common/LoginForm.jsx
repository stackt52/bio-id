import {Button, Container, InputAdornment, TextField} from "@mui/material";
import Box from "@mui/material/Box";
import {Lock, Person} from "@mui/icons-material";
import {grey} from "@mui/material/colors";
import {useContext, useState} from "react";
import {AuthDispatchContext} from "../../context/Default";
import {postItem} from "../../util/functions";

export default function LoginForm() {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const dispatch = useContext(AuthDispatchContext)

    const login = (e) => {
        postItem("/auth/users/login", {username, password})
            .then(data => {
                const {username, name, token} = data
                dispatch(
                    {
                        type: "SET_USER",
                        payload: {username, name, token}
                    }
                )
                console.log(data)
            }).catch(err => console.log(err));
    }

    return (
        <Container sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            minHeight: '100vh'
        }}>
            <Box sx={{fontSize: 'h5.fontSize', fontWeight: 'bold', my: 1}}>LOGIN</Box>
            <Box sx={{fontSize: 'body2.fontSize', color: grey[600], my: 1}}>Eyo! Good to see ya ;)</Box>
            <TextField
                sx={{my: 1}}
                label="Username"
                placeholder="Enter your username"
                variant="filled"
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

            <Button sx={{my: 1}} variant="contained" size="small" onClick={login}>LogIn</Button>
        </Container>
    )
}