import {useEffect, useState} from "react";
import Grid from "@mui/material/Unstable_Grid2";
import {FormControl, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import {DatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import * as React from "react";

export default function BasicClientProfileForm({personalDetails, setPersonalDetails}) {
    const [dateOfBirth, setDateOfBirth] = useState(null);
    const updateDetails = (e) => {
        setPersonalDetails({...personalDetails, [e.target.name]: e.target.value})
    }

    useEffect(() => {
        if (dateOfBirth) {
            const dob = dateOfBirth.format('YYYY-MM-DD')
            setPersonalDetails({...personalDetails, dateOfBirth: dob})
        }
    }, [dateOfBirth])

    return (
        <Grid container spacing={2}>
            <Grid xs={4}>
                <TextField
                    autoFocus
                    fullWidth
                    label="First name"
                    name="firstName"
                    required={true}
                    value={personalDetails.firstName}
                    onChange={updateDetails}></TextField>
            </Grid>
            <Grid xs={4}>
                <TextField fullWidth
                           label="Middle name"
                           name="middleName"
                           value={personalDetails.middleName}
                           onChange={updateDetails}></TextField>
            </Grid>
            <Grid xs={4}>
                <TextField fullWidth
                           label="Last name"
                           name="lastName"
                           required={true}
                           value={personalDetails.lastName}
                           onChange={updateDetails}></TextField>
            </Grid>
            <Grid xs={4}>
                <FormControl fullWidth>
                    <InputLabel id="sex-select-label">Sex</InputLabel>
                    <Select
                        required={true}
                        labelId="sex-select-label"
                        id="sex-select"
                        name="sex"
                        label="Sex"
                        value={personalDetails.sex} onChange={updateDetails}>
                        <MenuItem value="M">Male</MenuItem>
                        <MenuItem value="F">Female</MenuItem>
                    </Select>
                </FormControl>
            </Grid>
            <Grid xs={4}>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DatePicker disableFuture
                                label="Date of birth"
                                value={dateOfBirth}
                                onChange={(v) => setDateOfBirth(v)}
                                sx={{width: '100%'}}/>
                </LocalizationProvider>
            </Grid>
            <Grid xs={4}/>
            <Grid xs={4}>
                <TextField fullWidth
                           label="Auxiliary ID name"
                           name="clientIdName"
                           value={personalDetails.clientIdName}
                           onChange={updateDetails}></TextField>
            </Grid>
            <Grid xs={4}>
                <TextField fullWidth
                           label="Auxiliary ID value"
                           name="clientIdValue"
                           required={true}
                           value={personalDetails.clientIdValue}
                           onChange={updateDetails}></TextField>
            </Grid>
        </Grid>
    )
}