import React, { Component } from 'react';
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 

export default class Supply extends Component {
    static displayName = Supply.name;

    constructor(props) {
        super(props);
        this.state = { 
            supplyError: undefined,
            country: undefined,
            city: undefined,
            street: undefined,
            apartmentnumber: undefined,
            snackbar: null,
        };

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

    async supply(){
        let country = this.state.country;
        let city = this.state.city;
        let street = this.state.street;
        let apartmentnumber = this.state.apartmentnumber;
        console.log("country is "+country+" , city is "+city+" , street is "+street+" , apartmentnumber is "+ apartmentnumber+"\n");
        // let response = await ConnectApi.register(email, password, firstname, lastname);
        // alert(response.message);
    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <h3>Supply</h3>
                        <form class="LoginForm" onSubmit={this.handleSubmit}>
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="country" name="country" value={this.state.country}
                                    placeholder="Country" required/>
                            <input type="city" name="city" value={this.state.city}
                                    placeholder="City" required/>
                            <input type="street" name="street" value={this.state.street}
                                    placeholder="Street" required/>
                            <input type="apartmentnumber" name="apartmentnumber" value={this.state.apartmentnumber}
                                    placeholder="Apartment no." required/>
                            
                            {/* <select name="role" value={this.state.role} required>
                                <option value="member">Member</option>
                                <option value="admin">Admin</option>
                            </select> */}
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <Button onClick={() => this.supply()} variant="contained">Send </Button>
                                {/* <input class="action" type="submit" value="Login"/> */}
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