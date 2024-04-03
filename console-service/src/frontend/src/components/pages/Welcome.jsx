import {Container} from "@mui/material";
import Box from "@mui/material/Box";

export default function Welcome() {
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