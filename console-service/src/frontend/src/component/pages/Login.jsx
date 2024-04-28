import Box from "@mui/material/Box";
import {Container} from "@mui/material";
import LoginForm from "../common/LoginForm";
import {useContext, useEffect} from "react";
import {AuthContext} from "../../context/Default";
import {useNavigate} from "react-router-dom";
import initRequestObject from "../../util/api";

export default function Login() {
    const auth = useContext(AuthContext)
    const navigate = useNavigate()

    useEffect(() => {
        if (auth.user) {
            initRequestObject() // reinitialize request obj to add auth headers
            navigate("/dashboard")
        }
    }, [auth])

    return (
        <Box sx={{display: 'flex', flexDirection: 'row', minHeight: '100vh', alignItems: 'stretch'}}>
            <Container sx={{flex: '2'}}>
                <LoginForm></LoginForm>
            </Container>

            <Container sx={{
                backgroundImage: 'linear-gradient(to bottom right, #0021f3, #0277bd)',
                flex: '1',
                padding: '20px',
                position: 'relative',
                overflow: 'hidden'
            }}>
                <Box sx={{
                    fontFamily: 'Monospace',
                    fontSize: 'h5.fontSize',
                    fontWeight: 'bold',
                    color: 'primary.contrastText'
                }}>BioID</Box>

                <Box sx={{
                    my: '10px',
                    fontSize: 'body2.fontSize',
                    color: 'primary.contrastText'
                }}>A centralized identity verification system.</Box>

                <Box sx={{
                    width: '320px',
                    height: '320px',
                    position: 'absolute',
                    borderRadius: '50%',
                    border: '2px solid #ffeb3b',
                    bottom: '-40%',
                    right: '15%'
                }}></Box>

                <Box sx={{
                    width: '400px',
                    height: '400px',
                    position: 'absolute',
                    borderRadius: '50%',
                    border: '2px solid #f4511e',
                    bottom: '-20%',
                    right: '-60%'
                }}></Box>
            </Container>
        </Box>
    )
}