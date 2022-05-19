import React, { Component } from 'react';
import "./Login.css"
const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

export default class Login extends Component {
    static displayName = Login.name;

    constructor(props) {
        super(props);
        this.state = { 
            loginError: undefined,
            username: undefined,
            password: undefined,
            role: "member",
            submitted: this.props.isLoggedIn
        };
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


    //async componentDidUpdate()
    
    async componentDidMount() {
        const response =await axios.get(EMPLOYEE_BASE_REST_API_URL).then(res => res).catch(err => err);
        console.log(response.data);
        let x = response;
        //let y = new Gson().fromjson(x);
        this.setState({
            ["username"]: response.data.wasException
        });
        

        return response.data;

    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                        <h3>Login</h3>
                        <form class="LoginForm" onSubmit={this.handleSubmit}>
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="text" name="username" value={this.state.username}
                                    placeholder="Username1" required/>
                            <input type="password" name="password" value={this.state.password}
                                    placeholder="Password" required/>
                            <select name="role" value={this.state.role} required>
                                <option value="member">Member</option>
                                <option value="admin">Admin</option>
                            </select>
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <input class="action" type="submit" value="Login"/>
                            </div>
                        </form>
                    </div>
                </main>
            );
        
    }
}