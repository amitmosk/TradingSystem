import React, { Component } from 'react';
import Button from '@mui/material/Button';
import { ConnectApi } from '../API/ConnectApi';
import HomeIcon from '@mui/icons-material/Home';
import { Navigate } from 'react-router-dom'; 
import Link from '@mui/material/Button';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import TextField from '@mui/material/TextField';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import { Utils } from '../ServiceObjects/Utils';

export default class Register extends Component {
    static displayName = Register.name;

    constructor(props) {
        super(props);
        this.state = { 
            registerError: undefined,
            email: "",
            password: "",
            firstname: "",
            lastname: "",
            birthdate : "1996-12-15",
            snackbar: null,
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
    

    async componentDidMount() {
    }
    

    async register(event){
        const {email, password, firstname, lastname, birthdate} = this.state;
        console.log("email is "+email+" , password is "+password+" firstname is "+firstname+" lastname is "+lastname+" birthdate is "+birthdate+"\n");
        if(Utils.check_email(email) == 0)
        {
            this.setState({ snackbar: { children: "Illegal Email", severity: "success" } });
            return;
        }
        if(Utils.check_holder(firstname) == 0)
        {
            this.setState({ snackbar: { children: "Illegal First Name", severity: "success" } });
            return;
        }
        if(Utils.check_holder(lastname) == 0)
        {
            this.setState({ snackbar: { children: "Illegal Last Name", severity: "success" } });
            return;
        }
        // if(Utils.check_birthdate(birthdate) == 0)
        // {
        //     this.setState({ snackbar: { children: "Illegal Birth Date", severity: "success" } });
        //     return;
        // }
        let response = await this.connectApi.register(email, password, firstname, lastname, birthdate);
        // alert(response.message);
        if (!response.was_exception)
        {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            await Utils.sleep(2000)
            const user = response.value;
            this.props.updateUserState(user);
    
            return (<Navigate to="/"/>)
        }
        else{
            this.setState({ snackbar: { children: response.message, severity: "error" } });

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
        
            return (this.props.user.state == 0 ? 
                <main className="LoginMain">
                    <div className="LoginWindow">
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
                            {/* <input type="date" name="birthdate" value={this.state.birthdate} onChange={this.handleInputChange}
                                    placeholder="Birth date - yyyy-mm-dd" required/>  */}
                            <input type="date" placeholder="Birth date" name="birthdate" value={this.state.birthdate} onChange={this.handleInputChange}
                                min="1900-01-01" max="2022-06-29"></input>
                            {/* <LocalizationProvider dateAdapter={AdapterDateFns}>
                                <DesktopDatePicker label="Birth date" value={this.state.b_d} minDate={new Date('1900-01-01')} maxDate={new Date('2022-026-06')} onChange={(newValue) => {this.parse_date(newValue);}} renderInput={(params) => <TextField {...params} />}/>   
                            </LocalizationProvider> */}
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
                </main> : 
                <Navigate to="/"/>
            ) ; 
        
    }
}