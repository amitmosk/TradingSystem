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
import FormDialog from './FormDialog';
import { Utils } from '../ServiceObjects/Utils';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));



export default class ViewAppointmentsStatus extends Component {

    constructor(props) {
        super(props);
        this.state = {
            store_id:this.props.store_id,
            appointments_agreements: [],
            manager_answer_appointment_fields:["Candidate Email"],
            snackbar: null,
        };
        this.storeApi = new StoreApi();
    }

    async view_appointments_status() {
        console.log("hey amit");
        let response = await this.storeApi.view_appointments_status(this.state.store_id);
        if (!response.was_exception) {
            console.log(response);
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            this.setState({ appointments_agreements: response.value });
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });


        }
    }
    async manager_answer_appointment(values) {
        const candidate_email = values[0]; 
        //This Will be true / false due to manager answer
        // the buttons will be Yes/or No after merge
        const manager_answer = values[1];
        console.log(values);
        // if(Utils.check_email(candidate_email) == 0)
        // {
        //     this.setState({ snackbar: { children: "Illegal Email", severity: "error" } });
        //     return;
        // }
        let response = await this.storeApi.manager_answer_appointment(this.state.store_id, manager_answer, candidate_email);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });


        }
    }

    async componentDidMount() {
        this.view_appointments_status();
    }

    render() {
        return (
            <>
                <Box position='center' align='center'>
                    <Grid position='center' row-spacing={3}>
                        <Grid item>
                            <h3 class="Header" align="center">
                                Appointments Status
                            </h3>
                        </Grid>
                        <Grid position='center' align='center'>
                            <Demo>
                                <List>
                                    {
                                        this.state.appointments_agreements.map((appointment) => (
                                            <ListItem>
                                                <ListItemAvatar>
                                                    <Avatar>
                                                    <ManageAccountsIcon></ManageAccountsIcon>
                                                    </Avatar>
                                                </ListItemAvatar>
                                                <ListItemText
                                                    primary={`Candidate :  ${appointment.member_email}    For:  ${appointment.type}  Appointed by:   ${appointment.appointer_email}`}
                                                    secondary={`Appointment status: ${appointment.status}`}
                                                />
                                            </ListItem>
                                            

                                        ))




                                    }
                                    <FormDialog fields={this.state.manager_answer_appointment_fields} getValues={this.manager_answer_appointment.bind(this)}  name="Answer Appointment" title={"Approve the Appointment?"} submit_button="Yes" cancel_button="No"></FormDialog>
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