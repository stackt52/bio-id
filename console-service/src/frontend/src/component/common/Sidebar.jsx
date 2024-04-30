import IconButton from "@mui/material/IconButton";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import Divider from "@mui/material/Divider";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import * as React from "react";
import {styled, useTheme} from "@mui/material/styles";
import {sidebarMenuList} from "../../constant/SidebarMenuList";
import {NavLink} from "react-router-dom";


const DrawerHeader = styled('div')(({theme}) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));


export default function Sidebar({toggleOpen, open}) {
    const theme = useTheme();

    return (
        <>
            <DrawerHeader>
                <IconButton onClick={toggleOpen}
                            sx={{transform: open ? 'rotate(180deg)' : 'rotate(0deg)'}}>
                    <ChevronRightIcon/>
                </IconButton>
            </DrawerHeader>
            <Divider/>
            <List>
                {sidebarMenuList.map((value, index) => (
                    <ListItem key={value.id} disablePadding sx={{display: 'block'}}>
                        <NavLink to={value.route}>
                            <ListItemButton
                                sx={{
                                    minHeight: 48,
                                    justifyContent: open ? 'initial' : 'center',
                                    px: 2.5,
                                }}
                            >
                                <ListItemIcon
                                    sx={{
                                        minWidth: 0,
                                        mr: open ? 3 : 'auto',
                                        justifyContent: 'center',
                                    }}
                                >
                                    {value.icon}
                                </ListItemIcon>
                                <ListItemText primary={value.label} sx={{opacity: open ? 1 : 0}}/>
                            </ListItemButton>
                        </NavLink>
                    </ListItem>
                ))}
            </List>
        </>
    )
}