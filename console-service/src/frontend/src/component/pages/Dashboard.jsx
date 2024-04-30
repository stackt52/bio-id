import * as React from 'react';
import {styled} from '@mui/material/styles';
import Box from '@mui/material/Box';
import MuiDrawer from '@mui/material/Drawer';
import MuiAppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Sidebar from "../common/Sidebar";
import IconButton from "@mui/material/IconButton";
import {Notifications, Person} from "@mui/icons-material";
import {Outlet, useNavigate} from "react-router-dom";
import {Container} from "@mui/material";
import {useContext, useEffect} from "react";
import {AuthContext} from "../../context/Default";


const drawerWidth = 240;

const DrawerHeader = styled('div')(({theme}) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

const openedMixin = (theme) => ({
    width: drawerWidth,
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
    }),
    overflowX: 'hidden',
});

const closedMixin = (theme) => ({
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: `calc(${theme.spacing(7)} + 1px)`,
    [theme.breakpoints.up('sm')]: {
        width: `calc(${theme.spacing(8)} + 1px)`,
    },
});

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})(({theme, open}) => ({
    //zIndex: theme.zIndex.drawer + 1,
    width: `calc(100% - ${theme.spacing(7)} + 1px)`,
    transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

const Drawer = styled(MuiDrawer, {shouldForwardProp: (prop) => prop !== 'open'})(
    ({theme, open}) => ({
        width: drawerWidth,
        flexShrink: 0,
        whiteSpace: 'nowrap',
        boxSizing: 'border-box',
        ...(open && {
            ...openedMixin(theme),
            '& .MuiDrawer-paper': openedMixin(theme),
        }),
        ...(!open && {
            ...closedMixin(theme),
            '& .MuiDrawer-paper': closedMixin(theme),
        }),
    }),
);

export default function Dashboard() {
    const [open, setOpen] = React.useState(false);

    const auth = useContext(AuthContext)
    const navigate = useNavigate()

    useEffect(() => {
        if (!auth.user)
            navigate("/login", {replace: true});
    }, [auth])

    const toggleOpen = () => {
        setOpen(!open);
    };

    return (
        <Box sx={{display: 'flex', bgcolor: 'grey.100', minHeight: '100vh'}}>
            <CssBaseline/>
            <AppBar position="fixed" open={open} sx={{boxShadow: 0, bgcolor: 'primary.contrastText'}}>
                <Toolbar>
                    <Typography variant="h6" noWrap component="div" sx={{color: 'common.black', flexGrow: 1}}>
                        Dashboard
                    </Typography>
                    <IconButton>
                        <Notifications/>
                    </IconButton>
                    <IconButton href="/login">
                        <Person/>
                    </IconButton>
                </Toolbar>
            </AppBar>
            <Drawer variant="permanent" open={open}>
                <Sidebar toggleOpen={toggleOpen} open={open}></Sidebar>
            </Drawer>
            <Box component="main" sx={{flexGrow: 1, p: 3}}>
                <DrawerHeader/>

                <Container>
                    <Outlet/>
                </Container>
            </Box>
        </Box>
    );
}