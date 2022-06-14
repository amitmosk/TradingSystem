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

const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));


export default class ViewStaffInformation extends Component {

    constructor(props) {
        super(props);
        this.state = {
            store_id:this.props.store_id,
            staff: [],
            snackbar: null,
        };
        this.storeApi = new StoreApi();
        console.log("in view stuff information, id = "+this.props.store_id);



    }

    async get_staff_info() {
        console.log("get store staff info\n");

        let response = await this.storeApi.view_store_management_information(this.state.store_id);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in get store staff info - success!\n");
            // return response.message;
            console.log(response.value.appointmentInformationList);
            // return response;
            this.setState({ staff: response.value.appointmentInformationList });
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
            // alert(response.message);


        }
    }

    async componentDidMount() {
        this.get_staff_info();
    }

    render() {
        return (

            <>
       
                <Box position='center' align='center'>
                    <Grid position='center' row-spacing={3}>
                        <Grid item>
                            <h3 class="Header" align="center">
                                Staff Information
                            </h3>
                        </Grid>

                        <Grid position='center' align='center'>

                            <Demo>
                                <List>
                                    {

                                        this.state.staff.map((staf) => (
                                            <ListItem>
                                                <ListItemAvatar>
                                                    <Avatar>
                                                        <PersonIcon />
                                                    </Avatar>
                                                </ListItemAvatar>
                                                <ListItemText
                                                    primary={staf.type + ': ' + staf.member_email}
                                                    secondary={'Appointed by: ' + staf.appointer_email}
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