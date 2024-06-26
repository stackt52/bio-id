import Typography from "@mui/material/Typography";
import * as React from "react";
import Box from "@mui/material/Box";
import {Fingerprint} from "@mui/icons-material";
import IconButton from "@mui/material/IconButton";
import {useEffect, useRef, useState} from "react";
import {postItem} from "../../util/functions";
import {enqueueSnackbar} from "notistack";
import {Avatar, ListItemAvatar} from "@mui/material";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import PersonIcon from '@mui/icons-material/Person';
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import {blue} from "@mui/material/colors";

export default function BioSearch() {
    const [fingerPrint, setFingerprint] = useState()
    const [client, setClient] = useState(null)
    const fileUploadRef = useRef(null);

    useEffect(() => {
        if (fingerPrint) {
            const payload = new FormData()
            payload.append("fingerprint", fingerPrint)
            postItem("/search", payload, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(data => {
                setClient(data)
                enqueueSnackbar("Client found with matching fingerprint", {variant: "info"})
            }).catch(e => {
                setClient(null)
                if (e.response.status === 404) {
                    enqueueSnackbar("No client matches fingerprint.", {variant: "warning"})
                }
            })
        }
    }, [fingerPrint])

    const onFileUpload = (e) => {
        e.preventDefault()
        e.stopPropagation()

        if (e.target.files.length === 1) {
            setFingerprint(fileUploadRef.current.files[0])
        }
    }

    const upload = (e) => {
        e.stopPropagation()
        fileUploadRef.current.click()
    }

    return (
        <Box sx={{px: 2, pb: 2}}>
            <Typography
                sx={{flex: '1 1 100%', my: 2}}
                variant="h6"
                id="tableTitle"
                component="div">
                Search by fingerprint
            </Typography>

            <input onChange={onFileUpload}
                   className="hidden"
                   type="file"
                   accept="image/*"
                   ref={fileUploadRef}/>

            <IconButton
                size="large"
                onClick={upload}
                sx={{width: 100, height: 100}}
            >
                <Fingerprint color="primary" sx={{fontSize: 80}}/>
            </IconButton>

            <Box sx={{mt: 3}}>
                {client && (
                    <List>
                        <Divider component="li" sx={{color: `${blue[500]}`}}/>
                        <Typography variant="overline">search result</Typography>
                        <ListItem>
                            <ListItemAvatar>
                                <Avatar>
                                    <PersonIcon/>
                                </Avatar>
                            </ListItemAvatar>
                            <ListItemText primary={client.firstName + ' ' + client.lastName}
                                          secondary={client.dateOfBirth}/>
                        </ListItem>
                    </List>

                )}
            </Box>
        </Box>
    )
}