import {Button, Container, InputAdornment, Link, TextField} from "@mui/material";
import Box from "@mui/material/Box";
import {Lock, Person} from "@mui/icons-material";
import {grey} from "@mui/material/colors";
import Divider from "@mui/material/Divider";
import {NavLink} from "react-router-dom";

export default function LogIn() {
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
                variant="filled"
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
                placeholder="Type your password"
                required={true}
                type="password"
                variant="filled"
                InputProps={{
                    startAdornment: (
                        <InputAdornment position="start">
                            <Lock/>
                        </InputAdornment>
                    ),
                }}/>

            <NavLink to="/dashboard">
                <Button sx={{my: 1}} variant="outlined" size="small">LogIn</Button>
            </NavLink>

            <Divider variant="middle" sx={{width: '200px', my: 2}}/>

            <Box sx={{fontSize: 'caption.fontSize', color: grey["500"], my: 1}}>Log-In using <Link href="#"
                                                                                                   underline="none">KEYCLOAK</Link>.</Box>
        </Container>
    )
}