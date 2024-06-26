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
import {Container, Menu, MenuItem} from "@mui/material";
import {useContext, useEffect, useState} from "react";
import {AuthContext, AuthDispatchContext, ProgressDispatchContext} from "../../context/Default";
import {AuthUserAction} from "../../util/constants";


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
    const [openDrawer, setOpenDrawer] = useState(false);
    const [openUserMenu, setOpenUserMenu] = useState(false)
    const [anchorElUser, setAnchorElUser] = useState(null);

    const authDispatch = useContext(AuthDispatchContext);
    const auth = useContext(AuthContext)
    const navigate = useNavigate()

    useEffect(() => {
        if (!auth.user)
            navigate("/login", {replace: true});
    }, [auth])

    const toggleOpenDrawer = () => {
        setOpenDrawer(!openDrawer);
    };

    const toggleOpenUserMenu = (e) => {
        if (openUserMenu)
            setAnchorElUser(null)
        else
            setAnchorElUser(e.currentTarget)
        setOpenUserMenu(!openUserMenu);
    }

    const logout = () => {
        setOpenUserMenu(false)
        authDispatch({type: AuthUserAction.reset})
    }

    return (
        <Box sx={{display: 'flex', bgcolor: 'grey.100', minHeight: '100vh'}}>
            <CssBaseline/>
            <AppBar position="fixed" open={openDrawer} sx={{boxShadow: 0, bgcolor: 'primary.contrastText'}}>
                <Toolbar>
                    <Typography variant="h6" noWrap component="div" sx={{color: 'common.black', flexGrow: 1}}>
                        Dashboard
                    </Typography>
                    <IconButton>
                        <Notifications/>
                    </IconButton>
                    <IconButton onClick={toggleOpenUserMenu}>
                        <Person/>
                    </IconButton>
                    <Menu
                        sx={{mt: '45px'}}
                        id="menu-appbar"
                        anchorEl={anchorElUser}
                        anchorOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        keepMounted
                        transformOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        open={openUserMenu}
                        onClose={() => setOpenUserMenu(false)}
                    >
                        <MenuItem key="profile">
                            <Typography textAlign="center">Profile</Typography>
                        </MenuItem>
                        <MenuItem key="logout" onClick={logout}>
                            <Typography textAlign="center">Logout</Typography>
                        </MenuItem>
                    </Menu>
                </Toolbar>
            </AppBar>
            <Drawer variant="permanent" open={openDrawer}>
                <Sidebar toggleOpen={toggleOpenDrawer} open={openDrawer}></Sidebar>
            </Drawer>
            <Box component="main" sx={{flexGrow: 1, p: 3, borderRadius: '5px'}}>
                <DrawerHeader/>

                <Container>
                    <Outlet/>
                </Container>
            </Box>
        </Box>
    );
}