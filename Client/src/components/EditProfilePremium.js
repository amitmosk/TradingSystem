// import * as React from 'react';
import React, { Component } from 'react';
import HomeIcon from '@mui/icons-material/Home';
import Box from '@mui/material/Box';
import Link from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import { UserApi } from '../API/UserApi';
import FormDialog from './FormDialog';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";


export default class EditProfileEditProfilePremium extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: this.props.name,
            lastName: this.props.lastName,
            security_question: "this.props.security_question",
            improve_security_fields: ["question", "answer","password"],
            edit_name_fields: ["new_name","security question answer"],
            edit_password_fields: ["old password","new password","new password again","security question answer"],
            unregister_fields: ["password"],


        }
        console.log("start edit profile");
        this.userApi = new UserApi();

    }

    async improve_security(values){
        const question = values[0];
        const answer = values[1];
        const password = values[2];

        let response =  await this.userApi.improve_security(password, question, answer);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
        }
        else {    
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }

    }
    async handle_name_edit_premium(values)  {
        console.log("in handle name edit premium\n");
        const new_name = values[0];
        const answer = values[1];
        let response = await this.userApi.edit_name_premium(new_name, answer);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in edit name premium - success!\n");
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }
    }



    
    async handle_last_name_edit_premium(values) {

        console.log("in handle last name edit premium\n");
        const new_name = values[0];
        const answer = values[1];
        let response = await this.userApi.edit_last_name_premium(new_name, answer);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
        }
        else {    
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }
    
    }


    async handle_password_edit_premium(values) {
        console.log("in handle password edit premium\n");
        let old_password = values[0];
        let new_password =values[1];
        let new_password_check = values[2];
        let answer = values[3];
        
        if (new_password == new_password_check) {
            let response =  await this.userApi.edit_password_premium(old_password, new_password, answer);
            // alert(response.message);
            if (!response.was_exception) {
                this.setState({ snackbar: { children: response.message, severity: "success" } });
            }
            else {
                this.setState({ snackbar: { children: response.message, severity: "error" } });
    
            }
    
        }
        else {
            console.log("in edit password - NOT THE SAME!\n");
    
        }
    }

    async unregister(values){
        const password = values[0];
        let response = await this.userApi.unregister(password);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            // set as a guest & go to home page.
        }
        else
        {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }

    }

    

    async componentDidMount() {
        console.log("did mount in edit profile : ");
        console.log(this.props.get_state());
      
    }

    render() {
        return (<>
            <Box sx={{ flexGrow: 1 }}>
                <Link href="/">
                    <HomeIcon></HomeIcon>
                </Link>
                <h3 class="Header" align="center">
                    Edit Profile Premium
                </h3>

                <Grid><FormDialog outlinedVar="text" title={this.state.security_question}
                    fields={this.state.edit_name_fields} getValues={this.handle_name_edit_premium.bind(this)}
                    name="edit name"></FormDialog></Grid> 

                <Grid><FormDialog outlinedVar="text" 
                    fields={this.state.edit_name_fields} getValues={this.handle_last_name_edit_premium.bind(this)}
                    name="edit last name"></FormDialog></Grid>

                <Grid><FormDialog outlinedVar="text" 
                    fields={this.state.edit_password_fields} getValues={this.handle_password_edit_premium.bind(this)}
                    name="edit password"></FormDialog></Grid> 

                <Grid><FormDialog outlinedVar="text" 
                    fields={this.state.improve_security_fields} getValues={this.improve_security.bind(this)}
                    name="Upgrade Security"></FormDialog></Grid> 

                <Grid><FormDialog outlinedVar="text" 
                    fields={this.state.unregister_fields} getValues={this.unregister.bind(this)}
                    name="unregister"></FormDialog></Grid> 

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

        </>)
    }
}