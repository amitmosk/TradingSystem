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
const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

// const useStyles = makeStyles({
//     input: {
//       width: 400,
//       height: 150,
//       '& input + fieldset': {
//         borderColor: 'hotpink',
//       },
//     },
//   });
  
//   function InputBox() {
//     const classes = useStyles();
  
//     return (
//       <TextField
//         InputProps={{
//           className: classes.input,
//         }}
//         id="outlined-basic"
//         variant="outlined"
//         label="Default"
//         defaultValue="Input Text"
//       />
//     );
//   }
  

export default class AdminSendMessage extends Component {
    static displayName = AdminSendMessage.name;

    constructor(props) {
        super(props);
        this.state = { 
            to: undefined,
            subject: undefined,
            content: undefined,
        };
        this.adminApi = new AdminApi();

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

    async send(){
        const {to, subject, content} = this.state;
        console.log("to is "+to+" , subject is "+subject+", content is "+content+"\n");
        const question = subject+"\n"+content;
        let response = await this.adminApi.admin_answer_user_question(question);
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
                        <h3>Send Messages - (Admin)</h3>
                        <form class="LoginForm" >
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <div>To</div>
                            <input type="text" name="email" value={this.state.email} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Email" required/>
                            <div>Subject</div>
                            <input type="password" name="password" value={this.state.password} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Password" required/>
                                    {/* <InputBox /> */}
                            
                            <div className="ConnectRegister">
                                
                                <Button onClick={() => this.send()} variant="contained">Send </Button>
                                
                            </div>
                        </form>
                    </div>
                </main>
            );
        
        }
    }
}