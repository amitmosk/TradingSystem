import React, { Component } from 'react';
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import { ConnectApi } from '../API/ConnectApi';
import { Navigate } from 'react-router-dom'; 
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import "./Login.css";
import SocketProvider from './SocketProvider';
import { Utils } from '../ServiceObjects/Utils';

export default class Login extends Component {
    static displayName = Login.name;

    constructor(props) {
        super(props);
        this.state = { 
            email: "",
            password: "",
            snackbar: null,
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
        console.log("email is "+email+" , password is "+password+"\n");
        if(Utils.check_email(email) == 0)
        {
            this.setState({ snackbar: { children: "Illegal Email", severity: "error" } });
            return;
        }
        if(Utils.check_not_empty(password) == 0)
        {
            this.setState({ snackbar: { children: "Illegal Password", severity: "error" } });
            return;
        }
        let response = await this.connectApi.login(email, password);
        // alert(response.message);
        if (!response.was_exception)
        {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            await Utils.sleep(2000)
            // login success
            const user = response.value;
            this.props.updateUserState(user);
            console.log("in login, login success!\n");
            const {createSocket} = SocketProvider(this.props.save);
            createSocket(user.email);
            // open seb socket
            // return to home page and update properties (change state of App to assign user).
            if (response.message == "The system is not available right now, come back later")
                this.setState({ snackbar: { children: response.message, severity: "success" } });
            return (<Navigate to="/" />)
        }
        else{
            this.setState({ snackbar: { children: response.message, severity: "error" } });

            // failure
            console.log("in login, login failed!\n");
        }
    }


    
    render() {
        const {redirectTo} = this.state
        if (this.props.user.state != 0) {
            
            console.log(this.props.user.state);
            console.log("have to route to homepage whe it will be ready\n\n\n");
            return (<Navigate to="/"/>);
        } else {
            return (
                <main className="LoginMain">
                    <div className="LoginWindow">
                    
                        <h3>Login</h3>
                        <form className="LoginForm" >

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