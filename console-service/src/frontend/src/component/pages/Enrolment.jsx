import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import EnrolmentForm from "../forms/EnrolmentForm";
import {useEffect, useState} from "react";
import AppDataGrid from "../common/AppDataGrid";
import Paper from "@mui/material/Paper";
import {getItem} from "../../util/functions";
import CustomTabPanel from "../common/CustomTabPanel";
import Typography from "@mui/material/Typography";
import BioSearch from "../common/BioSearch";

function a11yProps(index) {
    return {
        id: `tab-${index}`,
        'aria-controls': `tab-panel-${index}`,
    };
}

export default function Enrolment() {
    const list = [
        {
            id: 'firstName',
            numeric: false,
            disablePadding: true,
            label: 'First name',
        },
        {
            id: 'lastName',
            numeric: false,
            disablePadding: false,
            label: 'Last name',
        },
        {
            id: 'sex',
            numeric: false,
            disablePadding: false,
            label: 'Sex',
        },
        {
            id: 'dateOfBirth',
            numeric: false,
            disablePadding: false,
            label: 'Date of Birth',
        }
    ];
    const [value, setValue] = useState(0);
    const [data, setData] = useState([])
    const [orderBy, setOrderBy] = useState('firstName');
    const [selected, setSelected] = useState([]);
    const [headCells] = useState(list)

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    useEffect(() => {
        getItem('/enrolments').then(data => {
            console.log(data)
            setData(data)
        }).catch(e => {
            console.error(e)
        })
    }, [])

    return (
        <Paper sx={{bgcolor: 'primary.contrastText'}}>
            <Box sx={{width: '100%'}}>
                <Box sx={{borderBottom: 1, borderColor: 'divider'}}>
                    <Tabs value={value} onChange={handleChange} aria-label="tabs">
                        <Tab label="Search" {...a11yProps(0)} />
                        <Tab label="Enrolments" {...a11yProps(1)} />
                        <Tab label="New..." {...a11yProps(2)} />
                    </Tabs>
                </Box>
                <CustomTabPanel index={value} value={0}>
                    <BioSearch />
                </CustomTabPanel>
                <CustomTabPanel value={value} index={1}>
                    <AppDataGrid
                        heading="Enrolled clients"
                        data={data}
                        orderBy={orderBy}
                        selected={selected}
                        headCells={headCells}
                        setOrderBy={setOrderBy}
                        setSelected={setSelected}
                    />
                </CustomTabPanel>
                <CustomTabPanel value={value} index={2}>
                    <EnrolmentForm/>
                </CustomTabPanel>
            </Box>
        </Paper>
    )
}