import HomeIcon from '@mui/icons-material/Home';
import {CloudDone, Dns, EditNote, Fingerprint, ManageSearch, Settings} from "@mui/icons-material";

export const sidebarMenuList = [
    {
        id: 0,
        label: 'Home',
        icon: <HomeIcon/>,
        route: '/dashboard',
        children: []
    },
    {
        id: 1,
        label: 'Enrolment',
        icon: <Fingerprint/>,
        route: 'enrolment',
        children: [
            {
                id: 0,
                label: 'Manage',
                icon: <ManageSearch/>,
                route: ''
            },
            {
                id: 1,
                label: 'Create',
                icon: <EditNote />,
                route: ''
            }
        ]
    },
    {
        id: 2,
        label: 'Setting',
        icon: <Settings/>,
        route: 'settings',
        children: [
            {
                id: 0,
                label: 'Source systems',
                icon: <Dns/>,
                route: ''
            },
            {
                id: 1,
                label: 'Service availability',
                icon: <CloudDone />,
                route: ''
            }
        ]
    }
]