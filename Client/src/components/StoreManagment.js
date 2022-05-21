import React, { Component } from 'react';
import "./Login.css";
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import { ConnectApi } from '../API/ConnectApi';
import Register from "./Register.js";
import HomePageSearch from './HomePageSearch';
import {BrowserRouter, Route, Router, Routes} from "react-router-dom";
import {withRouter} from 'react-router-dom';
import { Navigate } from 'react-router-dom'; 
import { AdminApi } from '../API/AdminApi';
import { TextField,makeStyles  } from '@mui/material';
import { experimentalStyled as styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import FormDialog from './FormDialog';
const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  }));


const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";


  

export default class StoreManagment extends Component {
    static displayName = StoreManagment.name;

    constructor(props) {
        super(props);
        this.state = { 
            storeidtoshowhistory: undefined,
            useremailtoshowhistory: undefined,
            useremailtoremove: undefined,
            storeidtoremove: undefined,
            
        };
        this.adminApi = new AdminApi();

    }
    
    
    handleInputChange(event){
        const target = event.target;
        this.setState({
            [target.name]: target.value
        });
    }
    

    
    async componentDidMount() {
    }

    async get_market_stats(){
        
        let response = await this.adminApi.get_market_stats();
        const stats = response.value;
        if (!response.was_execption)
        {
            // Show stats

           
        }
        else{

        }
        alert(response.message);
    }
    async admin_view_store_purchases_history(){
        const store_id = this.state.storeidtoshowhistory;
        const response = await this.adminApi.admin_view_store_purchases_history(store_id);
        if (!response.was_execption)
        {
            //show history
        }
        else{

        }
    }
    async admin_view_user_purchases_history(){
        const user_email = this.state.useremailtoshowhistory;
        const response = await this.adminApi.admin_view_user_purchases_history(user_email);
        if (!response.was_execption)
        {
            //show history
        }
        else{
            
        }
    }

    async remove_user(){
        const user_email = this.state.useremailtoremove;
        const response = await this.adminApi.remove_user(user_email);
        if (!response.was_execption)
        {
            //show history
        }
        else{
            
        }
    }


    async close_store_permanently(){
        const store_id = this.state.storeidtoremove;
        const response = await this.adminApi.close_store_permanently(store_id);
        if (!response.was_execption)
        {
            //show history
        }
        else{
            
        }
    }
   
    
    render() {
        const {redirectTo} = this.state
        // { this.state.redirect ? (<Redirect push to="/"/>) : null }
        
            return (
                
                <Box sx={{ flexGrow: 1 }}>
                    <h3>Store Managment Page</h3>

                <Grid container spacing={20}>
                <Grid item xs>
                <FormDialog/>
              
                <input type="text" name="storeidtoremove" value={this.state.storeidtoremove} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Add Product" required/>
                <Button variant="contained">Add Product</Button>
                </Grid>
                <Grid item xs={3}>
                <input type="text" name="useremailtoremove" value={this.state.useremailtoremove} onChange={this.handleInputChange.bind(this)}
                                    placeholder="User Email To Remove" required/>
                <Button variant="contained">Remove User </Button>
                </Grid>
                <Grid item xs>
                <Button href="/AdminSendMessage" variant="contained">User Message </Button>
                </Grid>
                <Grid item xs>
                <input type="text" name="useremailtoshowhistory" value={this.state.useremailtoshowhistory} onChange={this.handleInputChange.bind(this)}
                                    placeholder="User Email To Show Purchase History" required/>
                <Button  variant="contained">User Purchase History </Button>
                </Grid>
                <Grid item xs>
                <input type="text" name="storeidtoshowhistory" value={this.state.storeidtoshowhistory} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Store ID To Show Purchase History" required/>
                <Button onClick={()=>this.admin_view_store_purchases_history} variant="contained">Store Purchase History </Button>
                </Grid>
                <Grid item xs>
                <Button onClick={()=>this.get_market_stats} variant="contained">Statistics </Button>
                </Grid>
                </Grid>

                <Box sx={{ flexGrow: 3 }}>

                </Box>
                    <h3> </h3>
                </Box>
                



                // <main class="LoginMain">
                //     <div class="LoginWindow">
                //         <h3>Admin Page</h3>
                //         <form class="LoginForm" >
                //         <Button href="/Register" variant="contained">Send </Button>
                //         <Button href="/Register" variant="contained">Send </Button>
                //         <Button href="/Register" variant="contained">Send </Button>
                //         <Button href="/Register" variant="contained">Send </Button>
                //         <Button href="/Register" variant="contained">Send </Button>
                //         <Button href="/Register" variant="contained">Send </Button>
                //         </form>
                //     </div>
                // </main>
            );
        
        }
    
}