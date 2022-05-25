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
import {User } from '../ServiceObjects/User';
// import {User} from '../ServiceObjects/User'
const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";
const WEBSOCKETURL = "ws://localhost:8080/chat";


export default class Login extends Component {
    static displayName = Login.name;

    constructor(props) {
        super(props);
        this.state = { 
            loginError: undefined,
            email: undefined,
            password: undefined,
            // submitted: this.props.isLoggedIn
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


    async open_web_socket(){
        const websocket = require('ws');
        var ws = new websocket(WEBSOCKETURL);
        
        ws.onopen = function(data) {ws.send("-client- want to open web socket with the server");};
        ws.onmessage = function(data) {
            alert("new notification!");
            // update notifications UI with the new notification 
            console.log(data);
         }
    }


    async componentDidMount() {
    }

    async login(){
        const {email, password} = this.state;
        console.log("email is "+email+" , password is "+password+"\n");
        let response = await this.connectApi.login(email, password);
        alert(response.message);
        if (!response.was_exception)
        {
            // login success
            const user = response.value;
            this.props.updateUserState(user);
            console.log("in login, login success!\n");
            // open seb socket
            // this.open_web_socket();
            // return to home page and update properties (change state of App to assign user).
            return (<Navigate to="/"/>)
        }
        else{
            // failure
            console.log("in login, login failed!\n");
        }
    }


    
    render() {
        const {redirectTo} = this.state
        // { this.state.redirect ? (<Redirect push to="/"/>) : null }
        // if (this.state.submitted) {
        if (this.props.state != 0) {
            
            console.log("have to route to homepage whe it will be ready\n\n\n");
            return (<Navigate to="/"/>);
        } else {
            return (
                <main className="LoginMain">
                    <div className="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <h3>Login</h3>
                        <form className="LoginForm" >
                            {this.state.loginError ?
                                <div className="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="text" name="email" value={this.state.email} onChange={this.handleInputChange}
                                    placeholder="Email" required/>
                            <input type="password" name="password" value={this.state.password} onChange={this.handleInputChange}
                                    placeholder="Password" required/>
                            
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <Button onClick={() => this.login()} variant="contained">Login </Button>
                                {/* <Button onClick={() => this.logout()} variant="contained">logout </Button> */}
                                <Link href="/Register" underline="hover" >{'New user? Create new account'}</Link>
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