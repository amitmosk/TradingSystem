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

    }
    
    handleInputChange(event){
        const target = event.target;
        this.setState({
            [target.name]: target.value
        });
    }
    
    async handleSubmit(event){
        event.preventDefault();
        const {username, password, role} = this.state;
        const loginRedirectAndRes = await this.authApi.Login(username, password, role);
        if(loginRedirectAndRes) {
            const loginRes = loginRedirectAndRes.data;

            if (loginRes && loginRes.isSuccess) {
                this.props.loginUpdateHandler(username, this.getUserRole(role))
            } else {
                this.setState({
                    loginError: loginRes.error
                })
            }
        } else {
            this.setState({
                loginError: "You need to be a guest"
            })
        }
    }
    



    
    async componentDidMount() {
    }

    async login(){
        const {email, password} = this.state;
        this.props.loginUpdateHandler(email);
        console.log("email is "+email+" , password is "+password+"\n");
        let response = await ConnectApi.login(email, password);
        if (!response.was_execption)
        {
            // return <Navigate to="/HomePageSearch"/>

            //go to home page
            //change state of App to assign user
        }
        else{

        }
        alert(response.message);
    }
    
    render() {
        const {redirectTo} = this.state
        // { this.state.redirect ? (<Redirect push to="/"/>) : null }
        if (this.state.submitted) {
            console.log("have to route to homepage whe it will be ready\n\n\n");
            return <Navigate to="/HomePageSearch"/>
        } else {
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                        <h3>Login</h3>
                        <form class="LoginForm" >
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="text" name="email" value={this.state.email} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Email" required/>
                            <input type="password" name="password" value={this.state.password} onChange={this.handleInputChange.bind(this)}
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