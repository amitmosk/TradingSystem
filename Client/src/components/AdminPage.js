import React, { Component } from 'react';
import Link from '@mui/material/Button';
import { AdminApi } from '../API/AdminApi';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import HomeIcon from '@mui/icons-material/Home';
import FormDialog from './FormDialog';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";


export default class AdminPage extends Component {
    static displayName = AdminPage.name;

    constructor(props) {
        super(props);
        this.state = {

            close_store_fields: ["Store ID"],
            remove_user_fields: ["User Email"],
            admin_answer_user_question_fields: ["User Email"],
            admin_view_user_purchases_history_fields: ["User Email"],
            admin_view_store_purchases_history_fields: ["Store ID"],

        };
        this.adminApi = new AdminApi();
        this.handleInputChange = this.handleInputChange.bind(this);


    }


    handleInputChange(event) {
        const target = event.target;
        this.setState({
            [target.name]: target.value
        });
    }



    async componentDidMount() {
    }

 
    async admin_view_store_purchases_history(values) {
        console.log("in admin view store purchases history\n");
        const store_id = values[0];
        const response = await this.adminApi.admin_view_store_purchases_history(store_id);
        if (!response.was_exception) {
            console.log("in admin view store purchases history - success!\n");

            //show history
        }
        else {

        }
    }
    async admin_view_user_purchases_history(values) {
        console.log("in admin view user purchases history!\n");
        const user_email = values[0];
        const response = await this.adminApi.admin_view_user_purchases_history(user_email);
        if (!response.was_exception) {
            console.log("in admin view user purchases history - success!\n");
            //show history
        }
        else {

        }
    }

    async remove_user(values) {
        console.log("in remove user!\n");
        const user_email = values[0];
        const response = await this.adminApi.remove_user(user_email);
        // alert(response.message);
        
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in remove user - success!\n");
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }


    async close_store_permanently(values) {
        console.log("in close store permanently \n");
        const store_id = values[0];
        const response = await this.adminApi.close_store_permanently(store_id);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in close store permanently - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }


    async admin_answer_user_question(values) {
        console.log("in admin answer user question user!\n");
        const question_id = values[0];
        const answer = values[1];
        const response = await this.adminApi.admin_answer_user_question(question_id, answer);
        if (!response.was_exception) {
            console.log("in admin answer user question - success!\n");
            //show history
        }
        else {

        }
    }


    





    render() {
        const { redirectTo } = this.state
        return (

        <Box sx={{ flexGrow: 1 }}>
        <Link href="/"><HomeIcon></HomeIcon></Link>
        <h3 align="center">Admin Page</h3>

        <Grid container spacing={6} paddingRight={25} paddingLeft={25} paddingTop={10}>
            <Grid item xs={3}> <FormDialog fields={this.state.close_store_fields} getValues={this.close_store_permanently.bind(this)} name="Close Store"></FormDialog> </Grid>
            <Grid item xs={3}> <FormDialog fields={this.state.remove_user_fields} getValues={this.remove_user.bind(this)} name="Remove User"></FormDialog></Grid>
            <Grid item xs={3}>  <Link href="/AdminViewUserQuestions" underline="hover"> {'View user questions'}</Link> </Grid>
            {/* <Grid item xs={3}>  <FormDialog fields={this.state.admin_answer_user_question_fields} getValues={this.admin_answer_user_question.bind(this)} name="User Message"></FormDialog></Grid> */}
            <Grid item xs={3}>  <FormDialog fields={this.state.admin_view_user_purchases_history_fields} getValues={this.admin_view_user_purchases_history.bind(this)} name="View User Purchase History"></FormDialog></Grid>
            <Grid item xs={3}>  <FormDialog fields={this.state.admin_view_store_purchases_history_fields} getValues={this.admin_view_store_purchases_history.bind(this)} name="View Store Purchase History"></FormDialog></Grid>
            <Grid item xs={3}>  <Link href="/ViewStat" underline="hover"> {'Show Statistics'}</Link> </Grid>
            
    
    
    
    
   
            </Grid>
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

            <Box sx={{ flexGrow: 3 }}>

            </Box>
            <h3> </h3>
            </Box>




                    );

                }

}