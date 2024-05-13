import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import EnrolmentDialog from "../common/EnrolmentDialog";
import EnrolmentForm from "../forms/EnrolmentForm";
import {Fab} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import {useState} from "react";
import AppDataGrid from "../common/AppDataGrid";

function CustomTabPanel(props) {
    const {children, value, index, ...other} = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`tabpanel-${index}`}
            aria-labelledby={`tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{p: 3}}>
                    {children}
                </Box>
            )}
        </div>
    );
}

CustomTabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

function a11yProps(index) {
    return {
        id: `tab-${index}`,
        'aria-controls': `tabpanel-${index}`,
    };
}

export default function Enrolment() {
    const [openDialog, setOpenDialog] = useState(false);

    const handleClickOpen = () => {
        setOpenDialog(true);
    };
    const handleClose = () => {
        setOpenDialog(false);
    };
    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <Box sx={{bgcolor: 'primary.contrastText'}}>
            <Box sx={{width: '100%'}}>
                <Box sx={{borderBottom: 1, borderColor: 'divider'}}>
                    <Tabs value={value} onChange={handleChange} aria-label="tabs">
                        <Tab label="Enrolments" {...a11yProps(0)} />
                        <Tab label="New..." {...a11yProps(1)} />
                    </Tabs>
                </Box>
                <CustomTabPanel value={value} index={0}>
                    <AppDataGrid/>
                </CustomTabPanel>
                <CustomTabPanel value={value} index={1}>
                    <EnrolmentForm/>
                </CustomTabPanel>
            </Box>

            <EnrolmentDialog
                title="Enrol client"
                actionButtonLabel="enrol"
                open={openDialog}
                handleClose={handleClose}>
                <EnrolmentForm/>
            </EnrolmentDialog>

            <Fab color="primary" aria-label="add"
                 sx={{position: "absolute", bottom: "30px", right: "30px", zIndex: '100'}}>
                <AddIcon onClick={handleClickOpen}/>
            </Fab>
        </Box>
    )
}