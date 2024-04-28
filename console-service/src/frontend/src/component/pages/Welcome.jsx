import {Container} from "@mui/material";
import Box from "@mui/material/Box";
import {useContext, useEffect} from "react";
import {AuthContext} from "../../context/Default";
import {useNavigate} from "react-router-dom";

export default function Welcome() {
    const auth = useContext(AuthContext)
    const navigate = useNavigate()

    useEffect(() => {
        const timeout = setTimeout(() => {
            if (auth.user)
                navigate("/dashboard")
            else
                navigate("/login")
        }, 3000);

        return () => {
            clearTimeout(timeout)
        }
    }, [auth])

    return (
        <Container sx={{
            minHeight: '100vh',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center'
        }}>
            <Box sx={{
                padding: '10px 30px',
                fontFamily: 'Monospace',
                fontSize: 'h3.fontSize',
                fontWeight: 'bold',
                color: 'primary.contrastText',
                bgcolor: 'grey.900'
            }}>
                BioID
            </Box>
        </Container>
    )
}