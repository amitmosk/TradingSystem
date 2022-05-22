import React, { Component } from 'react';
import "./Login.css";
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import { ConnectApi } from '../API/ConnectApi';
import Register from "./Register.js";
import HomePageSearch from './HomePageSearch';
import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import { withRouter } from 'react-router-dom';
import { Navigate } from 'react-router-dom';
import { AdminApi } from '../API/AdminApi';
import { TextField, makeStyles } from '@mui/material';
import { experimentalStyled as styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import HomeIcon from '@mui/icons-material/Home';
import FormDialog from './FormDialog';
import { Input } from "@mui/material";

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));


const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";




export default class AddDiscount extends Component {
    static displayName = AddDiscount.name;

    constructor(props) {
        super(props);
        this.state = {

            close_store_fields: ["Store ID"],
            remove_user_fields: ["User Email"],
            admin_answer_user_question_fields: ["User Email"],
            admin_view_user_purchases_history_fields: ["User Email"],
            admin_view_store_purchases_history_fields: ["Store ID"],
            get_market_stats_fields: [],

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




    render() {
        const ADD = "Add ";
        const DISCOUNT_RULE = " Discount Rule";
        const { redirectTo } = this.state
        return (

            <Box sx={{ flexGrow: 1 }}>

                <Link href="/"><HomeIcon></HomeIcon></Link>
                <h3>Add Discount Rule</h3>

                <Grid container spacing={20}>
                    
                <Grid item xs={3}>  <Item variant="outlined"> <form class="HomePageSearchForm" >
                        <Input name="val" placeholder={ADD + this.state.option+ DISCOUNT_RULE} onChange={this.handleInputChange} required/>
                        <Button onClick={() => this.add_discount_rule(this.state.option)} variant="contained">Add </Button>
                        
                        
                        <select name="option" value={this.state.option} onChange={this.handleInputChange} required>
                            <option value="Simple">Simple</option>
                            <option value="Complex">Complex</option>
                            <option value="Composite And">Composite And</option>
                            <option value="Composite Or">Composite Or</option>
                            <option value="Composite Xor">Composite Xor</option>
                        </select>


                    </form></Item ></Grid>
                </Grid>

                <Box sx={{ flexGrow: 3 }}>

                </Box>
                <h3> </h3>
            </Box>




        );

    }

}