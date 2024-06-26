import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import Typography from "@mui/material/Typography";
import StepContent from "@mui/material/StepContent";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import * as React from "react";
import BasicClientProfileForm from "./BasicClientProfileForm";
import FingerprintDetailsForm from "./FingerprintDetailsForm";
import {useState} from "react";
import {postItem} from "../../util/functions";
import {enqueueSnackbar} from "notistack";

export default function EnrolmentForm() {
    const details = {
        firstName: '',
        middleName: '',
        lastName: '',
        sex: '',
        dateOfBirth: '',
        clientIdName: '',
        clientIdValue: ''
    }
    const [personalDetails, setPersonalDetails] = useState(details)
    const [fingerprintImages, setFingerprintImages] = useState([])

    const [activeStep, setActiveStep] = React.useState(0);

    const handleNext = () => {
        if (activeStep === 1) {
            if (fingerprintImages.length === 0) {
                enqueueSnackbar('Capture at least a fingerprint before submitting!', {variant: "error"})
                return
            }
            const payload = new FormData()
            for (const [key, value] of Object.entries(personalDetails)) {
                payload.append(key, value)
            }
            fingerprintImages.forEach(v => {
                payload.append(v.key, v.image, v.image.name)
            })
            postItem('/enrolments', payload, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then((data) => {
                console.log(data)
                if (data.newClient) {
                    enqueueSnackbar('Client enrolled successfully!', {variant: "success"})
                    setActiveStep((prevActiveStep) => prevActiveStep + 1);
                } else
                    enqueueSnackbar('Enrolment suspended. A client with matching fingerprint was found', {variant: "warning"})

            }).catch(e => {
                enqueueSnackbar('Failed to enroll client.', {variant: "error"})
                console.error(e)
            })
        } else {
            setActiveStep((prevActiveStep) => prevActiveStep + 1);
        }
    };

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    const handleReset = () => {
        setActiveStep(0);
        setPersonalDetails(details)
        setFingerprintImages([])
    };
    return (
        <Box sx={{px: 2}}>
            <Typography
                sx={{flex: '1 1 100%', my: 2}}
                variant="h6"
                id="tableTitle"
                component="div"
            >
                Enroll client
            </Typography>
            <Stepper activeStep={activeStep} orientation="vertical">
                <Step key="basicInformation">
                    <StepLabel>
                        Basic client profile
                    </StepLabel>
                    <StepContent>
                        <BasicClientProfileForm personalDetails={personalDetails}
                                                setPersonalDetails={setPersonalDetails}/>
                        <Box sx={{mb: 2}}>
                            <div>
                                <Button
                                    variant="contained"
                                    onClick={handleNext}
                                    sx={{mt: 1, mr: 1}}
                                >
                                    Continue
                                </Button>
                                <Button
                                    disabled
                                    onClick={handleBack}
                                    sx={{mt: 1, mr: 1}}
                                >
                                    Back
                                </Button>
                            </div>
                        </Box>
                    </StepContent>
                </Step>
                <Step key="biometricFingerprints">
                    <StepLabel
                        optional={
                            <Typography variant="caption">Last step</Typography>
                        }
                    >
                        Fingerprint details
                    </StepLabel>
                    <StepContent>
                        <FingerprintDetailsForm fingerprintImages={fingerprintImages}
                                                setFingerprintImages={setFingerprintImages}/>
                        <Box sx={{mb: 2}}>
                            <div>
                                <Button
                                    variant="contained"
                                    onClick={handleNext}
                                    sx={{mt: 1, mr: 1}}
                                >
                                    Submit
                                </Button>
                                <Button
                                    onClick={handleBack}
                                    sx={{mt: 1, mr: 1}}
                                >
                                    Back
                                </Button>
                            </div>
                        </Box>
                    </StepContent>
                </Step>
            </Stepper>
            {activeStep === 2 && (
                <Paper square elevation={0} sx={{p: 3}}>
                    <Typography>Client has been successfully enrolled!</Typography>
                    <Button onClick={handleReset} sx={{mt: 1, mr: 1}}>
                        Done
                    </Button>
                </Paper>
            )}
        </Box>
    )
}