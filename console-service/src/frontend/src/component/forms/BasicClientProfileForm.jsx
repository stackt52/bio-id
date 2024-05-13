import {useState} from "react";
import Grid from "@mui/material/Unstable_Grid2";
import {FormControl, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import {DatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import * as React from "react";

export default function BasicClientProfileForm() {
    const [sex, setSex] = useState('')

    return (
        <Grid container spacing={2}>
            <Grid xs={4}>
                <TextField
                    autoFocus
                    fullWidth
                    label="First name"
                    name="firstName"
                    required={true}></TextField>
            </Grid>
            <Grid xs={4}>
                <TextField fullWidth
                           label="Middle name"
                           name="middleName"></TextField>
            </Grid>
            <Grid xs={4}>
                <TextField fullWidth
                           label="Last name"
                           name="lastName"
                           required={true}></TextField>
            </Grid>
            <Grid xs={4}>
                <FormControl fullWidth>
                    <InputLabel id="sex-select-label">Sex</InputLabel>
                    <Select
                        labelId="sex-select-label"
                        id="sex-select"
                        label="Sex"
                        value={sex}>
                        <MenuItem value="M">Male</MenuItem>
                        <MenuItem value="F">Female</MenuItem>
                    </Select>
                </FormControl>
            </Grid>
            <Grid xs={4}>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DatePicker label="Date of birth" sx={{width: '100%'}}/>
                </LocalizationProvider>
            </Grid>
            <Grid xs={4}/>
            <Grid xs={4}>
                <TextField fullWidth
                           label="Auxiliary ID name"
                           name="clientIdName"></TextField>
            </Grid>
            <Grid xs={4}>
                <TextField fullWidth
                           label="Auxiliary ID value"
                           name="clientIdValue"
                           required={true}></TextField>
            </Grid>
        </Grid>
    )
}