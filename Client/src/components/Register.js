import React, { Component } from 'react';
import "./Login.css";
import Button from '@mui/material/Button';
import { ConnectApi } from '../API/ConnectApi';

const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

export default class Login extends Component {
    static displayName = Login.name;

    constructor(props) {
        super(props);
        this.state = { 
            registerError: undefined,
            email: undefined,
            password: undefined,
            firstname: undefined,
            lastname: undefined,
        };

    }
    
    // handleInputChange(event){
    //     const target = event.target;
    //     this.setState({
    //         [target.name]: target.value
    //     });
    // }
    
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
    
    // getUserRole(role){
    //     return role === "member" ? UserRole.Member :
    //         role === "admin" ? UserRole.Admin : 
    //         undefined
    // }


    //async componentDidUpdate()
    
    async componentDidMount() {
        // const response =await axios.get(EMPLOYEE_BASE_REST_API_URL).then(res => res).catch(err => err);
        // console.log(response.data);
        // let x = response;
        // // this.setState({
        // //     ["email"]: "response.data.wasException"
        // // });
        

        // return response.data;

    }

    async register(){
        let email = this.state.email;
        let password = this.state.password;
        let firstname = this.state.firstname;
        let lastname = this.state.lastname;
        console.log("email is "+email+" , password is "+password+"\n");
        let response = await ConnectApi.register(email, password, firstname, lastname);
        alert(response.message);
    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                        <h3>Register</h3>
                        <form class="LoginForm" onSubmit={this.handleSubmit}>
                            {this.state.registerError ?
                                <div class="CenterItemContainer"><label>{this.state.registerError}</label></div> : null}
                            <input type="text" name="email" value={this.state.email}
                                    placeholder="Email" required/>
                            <input type="password" name="password" value={this.state.password}
                                    placeholder="Password" required/>
                            <input type="firstname" name="firstname" value={this.state.firstname}
                                    placeholder="First name" required/>
                            <input type="lastname" name="lastname" value={this.state.lastname}
                                    placeholder="Last name" required/>
                            {/* <select name="role" value={this.state.role} required>
                                <option value="member">Member</option>
                                <option value="admin">Admin</option>
                            </select> */}
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <Button onClick={() => this.register()} variant="contained">Login </Button>
                                {/* <input class="action" type="submit" value="Login"/> */}
                            </div>
                        </form>
                    </div>
                </main>
            );
        
    }
}