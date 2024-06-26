import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import AppFormDialog from "../common/AppFormDialog";
import {Fab} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import AppDataGrid from "../common/AppDataGrid";
import {getItem, postItem} from "../../util/functions";
import Paper from "@mui/material/Paper";
import {useEffect, useState} from "react";
import CustomTabPanel from "../common/CustomTabPanel";
import ServiceAccountForm from "../forms/ServiceAccountForm";
import Typography from "@mui/material/Typography";
import {enqueueSnackbar} from "notistack";

function a11yProps(index) {
    return {
        id: `tab-${index}`,
        'aria-controls': `tab-panel-${index}`,
    };
}

export default function Setting() {
    const [openDialog, setOpenDialog] = useState(false);
    const list = [
        {
            id: 'name',
            numeric: false,
            disablePadding: true,
            label: 'Name',
        },
        {
            id: 'email',
            numeric: false,
            disablePadding: false,
            label: 'Email',
        },
        {
            id: 'active',
            numeric: false,
            disablePadding: false,
            label: 'Active',
        }
    ];
    const [data, setData] = useState([])
    const [orderBy, setOrderBy] = useState('name');
    const [selected, setSelected] = useState([]);
    const [headCells] = useState(list)
    const [formData, setFormData] = useState(null)

    useEffect(() => {
        getItem('/auth/users').then(data => {
            console.log(data)
            setData(data)
        }).catch(e => {
            console.error(e)
        })
    }, [])

    useEffect(() => {
        console.log(formData)
        if (formData) {
            const {name, email, password, activeState} = formData
            postItem("/auth/users", {name, email, password, active: activeState === 'on'}).then(d => {
                enqueueSnackbar("User created successfully.", {variant: "success"})
                setData([...data, d])
            }).catch(e => {
                enqueueSnackbar("Failed to create service account.", {variant: "error"})
                console.error(e)
            })
        }
    }, [formData])

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
        <Paper sx={{bgcolor: 'primary.contrastText'}}>
            <Box sx={{width: '100%'}}>
                <Box sx={{borderBottom: 1, borderColor: 'divider'}}>
                    <Tabs value={value} onChange={handleChange} aria-label="tabs">
                        <Tab label="Service Accounts" {...a11yProps(0)} />
                        <Tab label="Other settings" {...a11yProps(1)} />
                    </Tabs>
                </Box>
                <CustomTabPanel value={value} index={0}>
                    <AppDataGrid
                        heading="Service accounts"
                        data={data}
                        orderBy={orderBy}
                        selected={selected}
                        headCells={headCells}
                        setOrderBy={setOrderBy}
                        setSelected={setSelected}
                    />
                </CustomTabPanel>
                <CustomTabPanel value={value} index={1}>
                    <Typography variant="h6" sx={{p: 3}}>Other settings...</Typography>
                </CustomTabPanel>
            </Box>

            <AppFormDialog
                title="Service account"
                actionButtonLabel="Create"
                open={openDialog}
                setFormData={setFormData}
                handleClose={handleClose}>
                <ServiceAccountForm/>
            </AppFormDialog>

            <Fab color="primary" aria-label="add"
                 sx={{position: "absolute", bottom: "30px", right: "30px", zIndex: '100'}}>
                <AddIcon onClick={handleClickOpen}/>
            </Fab>
        </Paper>
    )
}