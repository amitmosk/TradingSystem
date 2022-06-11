import * as React from 'react';
import { Component } from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import Grid from '@mui/material/Grid';
import Link from '@mui/material/Button';
import PersonIcon from '@mui/icons-material/Person';
import { StoreApi } from '../API/StoreApi';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import HomeIcon from '@mui/icons-material/Home';
import SocketProvider from './SocketProvider';
const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));



export default class Notifications extends Component {

    constructor(props) {
        super(props);
        this.state = {
            notifications: [],
            snackbar: null,
        };
        console.log("in view stuff information, id = "+this.props.store_id);



    }

    get_notifications() {
        this.setState({ notifications : SocketProvider.get_notifications() });
    }

    async componentDidMount() {
        this.get_notifications();
    }

    render() {
        return (

            <>
       
                <Box position='center' align='center'>
                    <Grid position='center' row-spacing={3}>
                        <Grid item>
                            <h3 class="Header" align="center">
                                Notifications
                            </h3>
                        </Grid>

                        <Grid position='center' align='center'>

                            <Demo>
                                <List>
                                    {

                                        this.state.notifications.map((n) => (
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

                                        ))




                                    }
                                </List>
                            </Demo>
                        </Grid>
                    </Grid>
                </Box>
                {!!this.state.snackbar && (
                        <Snackbar
                        open
                        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
                        onClose={this.handleCloseSnackbar}
                        autoHideDuration={6000}
                        >
                        <Alert
                            {...this.state.snackbar}
                            onClose={this.handleCloseSnackbar}
                        />
                        </Snackbar>
                    )}

            </>
        );
        // )
        // );

    }
}