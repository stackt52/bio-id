import Grid from "@mui/material/Unstable_Grid2";
import {FormControlLabel, Switch, TextField} from "@mui/material";
import * as React from "react";
import {useState} from "react";


export default function ServiceAccountForm() {
    const [checked, setChecked] = useState(true)

    const handleChange = (e) => {
        setChecked(e.target.checked)
    }
    return (
        <Grid container spacing={2}>
            <Grid xs={12}>
                <TextField
                    autoFocus
                    fullWidth
                    label="Account name"
                    name="name"
                    required={true}></TextField>
            </Grid>
            <Grid xs={12}>
                <TextField fullWidth
                           required={true}
                           label="Email"
                           name="email"></TextField>
            </Grid>
            <Grid xs={12}>
                <TextField fullWidth
                           required={true}
                           label="Password"
                           type="password"
                           value="default"
                           name="password"></TextField>
            </Grid>
            <Grid xs={12}>
                <FormControlLabel required
                                  label="Active"
                                  control={
                                      <Switch checked={checked} onChange={handleChange} name="activeState"/>
                                  }/>
            </Grid>
        </Grid>
    )
}