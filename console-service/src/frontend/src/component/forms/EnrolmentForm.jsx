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


export default function EnrolmentForm() {
    const [activeStep, setActiveStep] = React.useState(0);

    const handleNext = () => {
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    const handleReset = () => {
        setActiveStep(0);
    };
    return (
        <>
            <Stepper activeStep={activeStep} orientation="vertical">
                <Step key="basicInformation">
                    <StepLabel>
                        Basic client profile
                    </StepLabel>
                    <StepContent>
                        <BasicClientProfileForm/>
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
                        <FingerprintDetailsForm />
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
                    <Typography>All steps completed - you&apos;re finished</Typography>
                    <Button onClick={handleReset} sx={{mt: 1, mr: 1}}>
                        Reset
                    </Button>
                </Paper>
            )}
        </>
    )
}