import React, { Component } from 'react';
import Link from '@mui/material/Button';
import { AdminApi } from '../API/AdminApi';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import FormDialog from './FormDialog';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import { Utils } from '../ServiceObjects/Utils';
import { empty } from 'ramda';

export default function AdminPage() {


    const close_store_fields = ["Store ID"];
    const remove_user_fields = ["User Email"];
    const admin_answer_user_question_fields = ["User Email"];
    const admin_view_user_purchases_history_fields = ["User Email"];
    const admin_view_store_purchases_history_fields= ["Store ID"];
    const adminApi = new AdminApi();
    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);




 
    const admin_view_store_purchases_history = async (values) => {
        console.log("in admin view store purchases history\n");
        const store_id = values[0];
		if(Utils.check_all_digits(store_id) == 0)
        {
            setSnackbar({ children: "Illegal Store ID", severity: 'error' });
            return;
        }
        window.location.href+=`/AdminViewStorePurchaseHistory/${store_id}`
        console.log("in admin view store purchases history - success!\n");
    }
    const admin_view_user_purchases_history = async(values) => {
        console.log("in admin view user purchases history!\n");
        const user_email = values[0];
        if(Utils.check_email(user_email) == 0)
        {
            setSnackbar({ children: "Illegal Email", severity: 'error' });
            return;
        }
        window.location.href+=`/AdminViewUserPurchaseHistory/${user_email}`
        // const response = await adminApi.admin_view_user_purchases_history(user_email);
        // if (!response.was_exception) {
        //     console.log("in admin view user purchases history - success!\n");
            //show history
            // setSnackbar({ children: response.message, severity: 'success' }); 
        // }
        // else {
        //     setSnackbar({ children: response.message, severity: 'errot' }); 
        // }
    }

    const remove_user = async(values)=> {
        console.log("in remove user!\n");
        const user_email = values[0];
        if(Utils.check_email(user_email) == 0)
        {
            setSnackbar({ children: "Illegal Email", severity: 'error' });
            return;
        }
        const response = await adminApi.remove_user(user_email);
        // alert(response.message);
        
        if (!response.was_exception) {
            setSnackbar({ children: response.message, severity: 'success' }); 
            console.log("in remove user - success!\n");
        }
        else {
            setSnackbar({ children: response.message, severity: 'error' }); 

        }
    }


    const close_store_permanently = async(values) => {
        console.log("in close store permanently \n");
        const store_id = values[0];
		if(Utils.check_all_digits(store_id) == 0)
        {
            setSnackbar({ children: "Illegal Store ID", severity: 'error' });
            return;
        }
        const response = await adminApi.close_store_permanently(store_id);
        if (!response.was_exception) {
            setSnackbar({ children: response.message, severity: 'success' }); 
            console.log("in close store permanently - success!\n");
        }
        else {
            setSnackbar({ children: response.message, severity: 'error' }); 

        }
    }


    


    





        return (

        <Box sx={{ flexGrow: 1 }}>
        <h3 align="center">Admin Page</h3>

        <Grid container spacing={6} paddingRight={25} paddingLeft={25} paddingTop={10}>
            <Grid item xs={3}> <FormDialog fields={close_store_fields} getValues={close_store_permanently} name="Close Store"></FormDialog> </Grid>
            <Grid item xs={3}> <FormDialog fields={remove_user_fields} getValues={remove_user} name="Remove User"></FormDialog></Grid>
            <Grid item xs={3}>  <Link href="/AdminViewUserQuestions" underline="hover"> {'View users questions'}</Link> </Grid>
            <Grid item xs={3}>  <FormDialog fields={admin_view_user_purchases_history_fields} getValues={admin_view_user_purchases_history} name="View User Purchase History"></FormDialog></Grid>
            <Grid item xs={3}>  <FormDialog fields={admin_view_store_purchases_history_fields} getValues={admin_view_store_purchases_history} name="View Store Purchase History"></FormDialog></Grid>
            <Grid item xs={3}>  <Link href="/ViewStat" underline="hover"> {'Show Statistics'}</Link> </Grid>
            
    
    
    
    
   
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

            <Box sx={{ flexGrow: 3 }}>

            </Box>
            <h3> </h3>
            </Box>




                    );

                
}