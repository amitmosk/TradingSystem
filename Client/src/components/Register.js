import React, { Component } from 'react';
import "./Login.css";
import Button from '@mui/material/Button';
import { ConnectApi } from '../API/ConnectApi';
import { toBeInTheDocument } from '@testing-library/jest-dom/dist/matchers';

const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

export default class Register extends Component {
    static displayName = Register.name;

    constructor(props) {
        super(props);
        this.state = { 
            registerError: undefined,
            email: undefined,
            password: undefined,
            firstname: undefined,
            lastname: undefined,
            birthdate: undefined,
        };
        this.connectApi = new ConnectApi(); 

    }
    
    handleInputChange(event){
        // event.preventDefault();
        
        console.log("in handleInputChange");
        const target = event.target;
        console.log(target);
        console.log(target.name);
        console.log(target.value);
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
                    registerError: loginRes.error
                })
            }
        } else {
            this.setState({
                registerError: "You need to be a guest"
            })
        }
    }

    async componentDidMount() {
    }

    async register(event){
        // event.preventDefault();
        const {email, password, firstname, lastname, birthdate} = this.state;
        console.log("email is "+email+" , password is "+password+" firstname is "+firstname+" lastname is "+lastname+" birthdate is "+birthdate+"\n");
        let response = await this.connectApi.register(email, password, firstname, lastname, birthdate);
        if (!response.was_exception)
        {
            const user = response.value;
            
            // return <Navigate to="/HomePageSearch"/>
        }
        alert(response.message);
    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                        <h3>Register</h3>
                        <form class="LoginForm" >
                            {this.state.registerError ?
                                <div class="CenterItemContainer"><label>{this.state.registerError}</label></div> : null}
                            <input type="email" name="email" value={this.state.email} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Email" required/>
                            <input type="password" name="password" value={this.state.password} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Password" required/>
                            <input type="firstname" name="firstname" value={this.state.firstname} onChange={this.handleInputChange.bind(this)}
                                    placeholder="First name" required/>
                            <input type="lastname" name="lastname" value={this.state.lastname} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Last name" required/>
                            <input type="birthdate" name="birthdate" value={this.state.birthdate} onChange={this.handleInputChange.bind(this)}
                                    placeholder="Birth date" required/>    
                            
                            {/* <select name="role" value={this.state.role} required>
                                <option value="member">Member</option>
                                <option value="admin">Admin</option>
                            </select> */}
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <Button onClick={() => this.register()} variant="contained">Register </Button>
                                {/* <input class="action" type="submit" value="Login"/> */}
                            </div>
                        </form>
                    </div>
                </main>
            );
        
    }
}