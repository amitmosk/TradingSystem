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

const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));



export default class ViewAppointmentsStatus extends Component {

    constructor(props) {
        super(props);
        this.state = {
            store_id:this.props.store_id,
            appointments_agreements: [],
            manager_answer_appointment_fields:["Candidate Email", "'Yes' for confirm , 'No' for reject"],
            snackbar: null,
        };
        this.storeApi = new StoreApi();
    }

    async view_appointments_status() {
        console.log("hey amit");
        let response = await this.storeApi.view_appointments_status(this.state.store_id);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            this.setState({ appointments_agreements: response.value });
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });


        }
    }
    async manager_answer_appointment(values) {
        const candidate_email = values[0]; 
        const manager_answer = values[1];
        if (Utils.check_yes_no() == 0)
        {
            this.setState({ snackbar: { children: "Answer must only be 'Yes' or 'No' ", severity: "error" } });
            return;
        } 
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
                                                    bid.id
                                                    </Avatar>
                                                </ListItemAvatar>
                                                <ListItemText
                                                    primary={"Candidate : "  + appointment.member_email + " For: "+ appointment.type +" Appointed by: " + appointment.appointer_email}
                                                    secondary={"Appointment status: "+appointment.status}
                                                />
                                            </ListItem>
                                            

                                        ))




                                    }
                                    <FormDialog fields={this.state.manager_answer_appointment_fields} getValues={this.manager_answer_appointment.bind(this)}  name="Answer Appointment"></FormDialog>
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