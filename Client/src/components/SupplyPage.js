import React, { Component } from 'react';
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { Supply } from '../ServiceObjects/Supply';
import { Utils } from '../ServiceObjects/Utils';
export default class SupplyPage extends Component {
    static displayName = SupplyPage.name;

    constructor(props) {
        super(props);
        this.state = { 
            supplyError: undefined,
            name: "",
            address: "",
            city: "",
            country: "",
            zip: "",
            snackbar: null,
        };

        this.handleInputChange = this.handleInputChange.bind(this);

    }

    handleInputChange(event){
        const target = event.target;
        console.log(target.name)
        console.log(target.value)
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

    async check_supply(){
        let name = this.state.name;
        let address = this.state.address;
        let city = this.state.city;
        let country = this.state.country;
        let zip = this.state.zip;
        console.log(name);
        console.log(address);
        console.log(city);
        console.log(country);
        console.log(zip);
        //TODO: check input validity
        if (Utils.check_holder(name)== 0)
        {
            this.setState({ snackbar: { children: "Illegal Name", severity: "error" } });
            return;
        }
        if (Utils.check_address(address)== 0)
        {
            this.setState({ snackbar: { children: "Illegal address", severity: "error" } });
            return;
        }
        if (Utils.check_city(city)== 0)
        {
            this.setState({ snackbar: { children: "Illegal city", severity: "error" } });
            return;
        }
        if (Utils.check_country(country)== 0)
        {
            this.setState({ snackbar: { children: "Illegal country", severity: "error" } });
            return;
        }
        if (Utils.check_zip(zip)== 0)
        {
            this.setState({ snackbar: { children: "Illegal zip code", severity: "error" } });
            return;
        }
        

        const supply_info = Supply.create(name, address, city, country, zip);
        this.props.update(supply_info);
    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                        <h3>Supply</h3>
                        <form class="LoginForm" onSubmit={this.handleSubmit}>
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="name" name="name" value={this.state.name} onChange={this.handleInputChange}
                                    placeholder="Name" required/>
                            <input type="address" name="address" value={this.state.address} onChange={this.handleInputChange}
                                    placeholder="Address" required/>
                            <input type="city" name="city" value={this.state.city} onChange={this.handleInputChange}
                                    placeholder="City" required/>
                            <input type="country" name="country" value={this.state.country} onChange={this.handleInputChange}
                                    placeholder="Country" required/>
                            <input type="zip" name="zip" value={this.state.zip} onChange={this.handleInputChange}
                                    placeholder="Zip" required/>
                            
                            {/* <select name="role" value={this.state.role} required>
                                <option value="member">Member</option>
                                <option value="admin">Admin</option>
                            </select> */}
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <Button onClick={() => this.check_supply()} variant="contained">Confirm</Button>
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