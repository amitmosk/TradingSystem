import React, { Component } from 'react';
import Button from '@mui/material/Button';
import { ConnectApi } from '../API/ConnectApi';
import { toBeInTheDocument } from '@testing-library/jest-dom/dist/matchers';
import HomeIcon from '@mui/icons-material/Home';
import { Navigate } from 'react-router-dom'; 
import Link from '@mui/material/Button';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import TextField from '@mui/material/TextField';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
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
            b_d : new Date(),
        };
        this.connectApi = new ConnectApi(); 
        this.handleInputChange = this.handleInputChange.bind(this);

    }
    
    handleInputChange(event){
        console.log("in handleInputChange");
        console.log(event.target.name);
        console.log(event.target.value);
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
        const {email, password, firstname, lastname, birthdate} = this.state;
        console.log("email is "+email+" , password is "+password+" firstname is "+firstname+" lastname is "+lastname+" birthdate is "+birthdate+"\n");
        let response = await this.connectApi.register(email, password, firstname, lastname, birthdate);
        alert(response.message);
        if (!response.was_exception)
        {
            const user = response.value;
            this.props.updateUserState(user);
            return (<Navigate to="/"/>)
        }
        else{

        }
    }

    parse_date = (date_to_parse) => {
        let year = date_to_parse.getFullYear()
        let month = date_to_parse.getMonth()
        let day = date_to_parse.getDay()
        let day_string = day<10?"0"+day:day
        let month_string = month<10?"0"+month:month
        this.setState({birthdate : year+"-"+month_string+"-"+day_string, b_d:date_to_parse})
    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main className="LoginMain">
                    <div className="LoginWindow">
                        <Link href="/"><HomeIcon></HomeIcon></Link>
                        <h3>Register</h3>
                        <form className="LoginForm" >
                            {this.state.registerError ?
                                <div className="CenterItemContainer"><label>{this.state.registerError}</label></div> : null}
                            <input type="email" name="email" value={this.state.email} onChange={this.handleInputChange}
                                    placeholder="Email" required/>
                            <input type="password" name="password" value={this.state.password} onChange={this.handleInputChange}
                                    placeholder="Password" required/>
                            <input type="firstname" name="firstname" value={this.state.firstname} onChange={this.handleInputChange}
                                    placeholder="First name" required/>
                            <input type="lastname" name="lastname" value={this.state.lastname} onChange={this.handleInputChange}
                                    placeholder="Last name" required/>
                            {/* <input type="birthdate" name="birthdate" value={this.state.birthdate} onChange={this.handleInputChange}
                                    placeholder="Birth date - yyyy-mm-dd" required/>  */}
                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                <DesktopDatePicker label="Birth date" value={this.state.b_d} minDate={new Date('1900-01-01')} onChange={(newValue) => {this.parse_date(newValue);}} renderInput={(params) => <TextField {...params} />}/>   
                            </LocalizationProvider>
                            {/* <select name="role" value={this.state.role} required>
                                <option value="member">Member</option>
                                <option value="admin">Admin</option>
                            </select> */}
                            <div className="ConnectRegister">
                                
                
                                <Button onClick={() => this.register()} variant="contained">Register </Button>
                                <Link href="/Login" underline="hover" >
                                {'Already registered? Login'}
                                </Link>
                            </div>
                        </form>
                    </div>
                </main>
            );
        
    }
}