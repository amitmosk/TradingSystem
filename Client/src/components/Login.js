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
const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

export default class Login extends Component {
    static displayName = Login.name;

    constructor(props) {
        super(props);
        this.state = { 
            loginError: undefined,
            email: undefined,
            password: undefined,
            submitted: this.props.isLoggedIn
        };
        this.connectApi = new ConnectApi(); 
        this.handleInputChange = this.handleInputChange.bind(this);
    }
    
    handleInputChange(event){
        const target = event.target;
        this.setState({
            [target.name]: target.value
        });
    }

    async componentDidMount() {
    }

    async login(){
        const {email, password} = this.state;
        this.props.updateLoginHandler();
        console.log("email is "+email+" , password is "+password+"\n");
        let response = await this.connectApi.login(email, password);
        if (!response.was_exception)
        {
            console.log("in login, login success!\n");
            console.log(response);
            // return <Navigate to="/HomePageSearch"/>
            <Navigate to="/"/>
            //go to home page
            //change state of App to assign user
        }
        else{
            console.log("in login, login failed!\n");
        }
        alert(response.message);
    }
    
    render() {
        const {redirectTo} = this.state
        // { this.state.redirect ? (<Redirect push to="/"/>) : null }
        if (this.state.submitted) {
            console.log("have to route to homepage whe it will be ready\n\n\n");
            return <Navigate to="/Album"/>
        } else {
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <h3>Login</h3>
                        <form class="LoginForm" >
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="text" name="email" value={this.state.email} onChange={this.handleInputChange}
                                    placeholder="Email" required/>
                            <input type="password" name="password" value={this.state.password} onChange={this.handleInputChange}
                                    placeholder="Password" required/>
                            
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <Button onClick={() => this.login()} variant="contained">Login </Button>

                                <Link href="/Register" underline="hover" >
                                {'New user? Cretae new account'}
                                </Link>
                                {/* <Button onClick={() => <Register/>} > registerr</Button> */}
                                {/* <input class="action" type="submit" value="Login"/> */}
                            </div>
                        </form>
                    </div>
                </main>
            );
        
        }
    }
}