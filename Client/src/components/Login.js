import React, { Component } from 'react';
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import { ConnectApi } from '../API/ConnectApi';
import { Navigate } from 'react-router-dom'; 
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
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
            // alert("new notification!");
            this.setState({ snackbar: { children: "new notifications!", severity: "success" } });

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
        // alert(response.message);
        if (!response.was_exception)
        {
            this.setState({ snackbar: { children: response.message, severity: "success" } });

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
            this.setState({ snackbar: { children: response.message, severity: "error" } });

            // failure
            console.log("in login, login failed!\n");
        }
    }


    
    render() {
        const {redirectTo} = this.state
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
                                
                                <Button onClick={() => this.login()} variant="contained">Login </Button>
                                <Link href="/Register" underline="hover" >{'New user? Create new account'}</Link>
                            </div>
                        </form>
                        {!!this.state.snackbar && (
                        <Snackbar
                        open
                        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
                        onClose={this.handleCloseSnackbar}
                        autoHideDuration={6000}
                        >
                        <Alert
                            {...this.state.snackbar}
                            onClose={this.handleCloseSnackbar}
                        />
                        </Snackbar>
                    )}
                    </div>
                </main>
            );
        
        }
    }
}