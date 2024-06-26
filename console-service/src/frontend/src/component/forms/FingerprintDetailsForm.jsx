import Box from "@mui/material/Box";
import {Chip, FormControl, InputLabel, MenuItem, Select, Stack} from "@mui/material";
import {fingerPrintPositions} from "../../constant/FingerPrintPosition";
import * as React from "react";
import {useRef, useState} from "react";
import Grid from "@mui/material/Unstable_Grid2";
import {grey} from "@mui/material/colors";
import IconButton from "@mui/material/IconButton";
import {Fingerprint} from "@mui/icons-material";
import Typography from "@mui/material/Typography";

export default function FingerprintDetailsForm({fingerprintImages, setFingerprintImages}) {
    const [fingerPosition, setFingerPosition] = useState('')
    const fileUploadRef = useRef(null);

    const onFileUpload = (e) => {
        e.preventDefault()
        e.stopPropagation()

        if (e.target.files.length === 1) {
            setFingerprintImages([
                ...fingerprintImages,
                {key: fingerPosition, image: fileUploadRef.current.files[0]}
            ])

            // Reset finger position and selected image
            setFingerPosition('')
            e.target.value = ""
        }
    }

    const upload = (e) => {
        e.stopPropagation()
        fileUploadRef.current.click()
    }

    const handleRemoveImage = (key) => {
        setFingerprintImages(fingerprintImages.filter(i => i.key !== key))
    }

    const handleFingerprintPositionChange = (event) => {
        setFingerPosition(event.target.value);
    };

    const chipLabel = (key) => {
        return fingerPrintPositions.find(i => i.key === key).label
    }

    return (
        <Box sx={{mb: 2}}>
            <Grid container spacing={2}>
                <Grid xs={6}>
                    <FormControl fullWidth>
                        <InputLabel id="finger-pos-select-label">Finger position</InputLabel>
                        <Select
                            labelId="finger-pos-select-label"
                            id="finger-pos-select"
                            label="Finger position"
                            value={fingerPosition}
                            onChange={handleFingerprintPositionChange}>
                            {fingerPrintPositions.map(i => (
                                <MenuItem
                                    key={i.key}
                                    value={i.key}
                                    disabled={fingerprintImages.find(v => v.key === i.key) != null}>{i.label}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid xs={6}/>
                <Grid xs={6}>
                    <input onChange={onFileUpload}
                           className="hidden"
                           type="file"
                           accept="image/*"
                           ref={fileUploadRef}/>

                    <Box sx={{
                        height: 150,
                        border: `2px dashed ${grey[300]}`,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        justifyContent: 'center'
                    }}>
                        <IconButton disabled={fingerPosition === ''}
                                    size="large"
                                    sx={{width: 100, height: 100}}
                                    onClick={upload}>
                            <Fingerprint sx={{fontSize: 80}}/>
                        </IconButton>
                    </Box>
                </Grid>
                <Grid xs={6}/>
                <Grid xs={12}>
                    <Stack hidden={fingerprintImages.length === 0}>
                        <Typography variant="subtitle2">Captured Fingerprints:</Typography>
                        <Box sx={{my: 1}}>
                            {fingerprintImages.map(i => (
                                <Chip
                                    sx={{mr: 1}}
                                    key={i.key}
                                    color="primary"
                                    label={chipLabel(i.key)}
                                    variant="outlined"
                                    onDelete={() => handleRemoveImage(i.key)}
                                />
                            ))}
                        </Box>
                    </Stack>
                </Grid>
            </Grid>
        </Box>
    )
}