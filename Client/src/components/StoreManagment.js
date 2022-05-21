import React, { Component } from 'react';
import "./Login.css";
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
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
import { StoreApi } from '../API/StoreApi';
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
            add_product_fields:["store_id", "quantity","name", "price", "category", "key_words"],
            arr:[]
            
        };
        this.adminApi = new AdminApi();
        this.storeApi = new StoreApi();

    }
    
    
    handleInputChange(event){
        const target = event.target;
        this.setState({
            [target.name]: target.value
        });
    }
    

    
    async componentDidMount() {
    }

    async add_product(){
        // let response = await this.adminApi.get_market_stats();
        // const stats = response.value;
        // if (!response.was_execption)
        // {
        //     // Show stats

           
        // }
        // else{

        // }
        // alert(response.message);
    }
    async add_product(values){
        
        console.log("in add product");
        console.log(values[0]);
        console.log(values[1]);
        console.log(values[2]);
        console.log(values[3]);
        console.log(values[4]);
        console.log(values[5]);
        // let response = await this.storeApi.add_product_to_store(values[0],values[1],values[2],values[3],values[4],values[5])

    }
    
   
    
    render() {
        const {redirectTo} = this.state
        // { this.state.redirect ? (<Redirect push to="/"/>) : null }
        
            return (
                
                <Box sx={{ flexGrow: 1 }}>
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                    <h3>Store Managment Page</h3>

                <Grid container spacing={20}>
                <Grid item xs>
                <FormDialog fields={this.state.add_product_fields} getValues={this.add_product.bind(this) } name="Add Product"></FormDialog>
                
                </Grid>
                <Grid item xs={3}>
                
                </Grid>
                <Grid item xs>
                </Grid>
                <Grid item xs>
               
                </Grid>
                <Grid item xs>
               
                </Grid>
                <Grid item xs>
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