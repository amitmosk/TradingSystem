import * as React from 'react';

import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import Grid from '@mui/material/Grid';
import PersonIcon from '@mui/icons-material/Person';
import { StoreApi } from '../API/StoreApi';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import HomeIcon from '@mui/icons-material/Home';
import SocketProvider from './SocketProvider';
import { ConnectApi } from '../API/ConnectApi';
import { useEffect } from 'react';
import { useState } from "react";

const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));


export default function Notifications() {
    const [user, setUser] = useState(null);
    const connectApi = new ConnectApi();
    // useEffect(()=>{get_online_user()}, []);
    useEffect(() => { get_notifications_list() }, []);

    const get_notifications_list = async () => {
        let response = await connectApi.get_notifications_list();
        if (!response.was_exception) {
            setNotifications(response.value);
            //  setSnackbar({ children: response.message, severity: 'success' });
        }
        else {
            setSnackbar({ children: response.message, severity: 'error' });

        }

    }

    const get_online_user = async () => {
        let response = await connectApi.get_online_user()
        if (!response.was_exception) {
            console.log(response.value);
            setUser(response.value);
            // get_notifications(response.value);
        }
        else {

        }

    }


    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);


    const [notifications, setNotifications] = React.useState([]);




    return (



        <Box position='center' align='center'>
            <Grid position='center' row-spacing={3} paddingTop={10}>
                <Grid item>
                    <h2 class="Header" align="center" >
                        Notifications
                    </h2>
                </Grid>

                <Grid position='center' align='center'>

                    <Demo>
                        <List>
                            {

                                notifications.length !== 0 ? notifications.map((n) => (
                                    <ListItem>
                                        <ListItemAvatar>
                                            <Avatar>
                                                <PersonIcon />
                                            </Avatar>
                                        </ListItemAvatar>
                                        <ListItemText
                                            primary={n}
                                            secondary={n}
                                        // primary='fsdf'
                                        />
                                    </ListItem>

                                )) : <h8 style={{ color: 'red' }}> No Notifications</h8>




                            }
                        </List>
                    </Demo>
                </Grid>
            </Grid>
            {!!snackbar && (
                <Snackbar
                    open
                    anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
                    onClose={handleCloseSnackbar}
                    autoHideDuration={6000}
                >
                    <Alert {...snackbar} onClose={handleCloseSnackbar} />
                </Snackbar>
            )}
        </Box>



    );


}          
