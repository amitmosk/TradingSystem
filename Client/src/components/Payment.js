import React, { Component } from 'react';
import "./Payment.css";
import Button from '@mui/material/Button';
import { ConnectApi } from '../API/ConnectApi';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

export default class Payment extends Component {
    static displayName = Payment.name;

    constructor(props) {
        super(props);
        this.state = { 
            paymentError: undefined,
            creditnumber: undefined,
            experationdate: undefined,
            cvv: undefined,
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
        // const response =await axios.get(EMPLOYEE_BASE_REST_API_URL).then(res => res).catch(err => err);
        // console.log(response.data);
        // let x = response;
        // // this.setState({
        // //     ["email"]: "response.data.was_exception"
        // // });
        

        // return response.data;

    }

    async pay(){
        let creditnumber = this.state.creditnumber;
        let experationdate = this.state.experationdate;
        let cvv = this.state.cvv;
        console.log("creditnumber is "+creditnumber+" , experationdate is "+experationdate+", experationdate is "+experationdate+"\n");
        // let response = await ConnectApi.register(email, password, firstname, lastname);
        // alert(response.message);
    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <h3>Payment</h3>
                        <form class="LoginForm" onSubmit={this.handleSubmit}>
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="creditnumber" name="creditnumber" value={this.state.creditnumber}
                                    placeholder="Credit Number" required/>
                            <input type="experationdate" name="experationdate" value={this.state.experationdate}
                                    placeholder="Experation Date" required/>
                            <input type="cvv" name="cvv" value={this.state.cvv}
                                    placeholder="CVV" required/>
                           
                            {/* <select name="role" value={this.state.role} required>
                                <option value="member">Member</option>
                                <option value="admin">Admin</option>
                            </select> */}
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <Button onClick={() => this.pay()} variant="contained">Pay </Button>
                                {/* <input class="action" type="submit" value="Login"/> */}
                            </div>
                        </form>
                    </div>
                </main>
            );
        
    }
}