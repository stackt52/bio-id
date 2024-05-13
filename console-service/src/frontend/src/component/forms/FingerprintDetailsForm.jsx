import Box from "@mui/material/Box";
import {FormControl, InputLabel, MenuItem, Select} from "@mui/material";
import {fingerPrintPositions} from "../../constant/FingerPrintPosition";
import * as React from "react";
import {useState} from "react";
import Grid from "@mui/material/Unstable_Grid2";
import {grey} from "@mui/material/colors";
import IconButton from "@mui/material/IconButton";
import {Fingerprint} from "@mui/icons-material";


export default function FingerprintDetailsForm() {
    const [fingerPosition, setFingerPosition] = useState('')

    return (
        <Box>
            <Grid container spacing={2}>
                <Grid xs={6}>
                    <FormControl fullWidth>
                        <InputLabel id="finger-pos-select-label">Finger position</InputLabel>
                        <Select
                            labelId="finger-pos-select-label"
                            id="finger-pos-select"
                            label="Finger position"
                            value={fingerPosition}>
                            {fingerPrintPositions.map(i => (
                                <MenuItem key={i.key} value={i.key}>{i.label}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid xs={6}/>
                <Grid xs={6}>
                    <input className="hidden" type="file" accept="image/*"/>
                    <Box sx={{
                        height: 150,
                        border: `2px dashed ${grey[300]}`,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        justifyContent: 'center'
                    }}>
                        <IconButton size="large" sx={{width: 100, height: 100}}>
                            <Fingerprint color="primary" sx={{fontSize: 80}}/>
                        </IconButton>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    )
}