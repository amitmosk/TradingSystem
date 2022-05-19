import React, { Component } from 'react';
import "./Login.css"
const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

export default class Register extends Component {
    static displayName = Register.name;

    constructor(props) {
        super(props);
        this.state = { 
            loginError: undefined,
            username: undefined,
            password: undefined,
            firstname: undefined,
            lastname: undefined,
            role: "member",
            submitted: this.props.isLoggedIn
        };
        this.componentDidMount();
        // this.authApi = new AuthApi();
        
        // this.handleInputChange = this.handleInputChange.bind(this);
        // this.handleSubmit = this.handleSubmit.bind(this);
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
                    loginError: loginRes.error
                })
            }
        } else {
            this.setState({
                loginError: "You need to be a guest"
            })
        }
    }
    
    // getUserRole(role){
    //     return role === "member" ? UserRole.Member :
    //         role === "admin" ? UserRole.Admin : 
    //         undefined
    // }
    
    componentDidMount() {
        this.setState({
            ["username"]: "y"
        });
        

        return response.data;

    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="RegisterMain">
                    <div class="RegisterWindow">
                        <h3>Register</h3>
                        <form class="RegisterForm" onSubmit={this.handleSubmit}>
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="text" name="username" value={this.state.username}
                                    placeholder="Username" required/>
                            <input type="password" name="password" value={this.state.password}
                                    placeholder="Password" required/>
                            <input type="firstname" name="firstname" value={this.state.firstname}
                                    placeholder="First Name" required/>
                            <input type="lastname" name="lastname" value={this.state.lastname}
                                    placeholder="Last Name" required/>
                            <select name="role" value={this.state.role} required>
                                <option value="member">Member</option>
                                <option value="admin">Admin</option>
                            </select>
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <input class="action" type="submit" value="Register"/>
                            </div>
                        </form>
                    </div>
                </main>
            );
        
    }
}