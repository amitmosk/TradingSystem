import React, { Component } from 'react';
import "./Login.css";
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import { ConnectApi } from '../API/ConnectApi';
import { CartApi } from '../API/CartApi';
import Register from "./Register.js";
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';



const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

export default class ShoppingCart extends Component {
    static displayName = ShoppingCart.name;

    constructor(props) {
        super(props);
        this.state = { 
            payment:undefined,
            supply:undefined,
        };
        
    }
    

    




    
    async componentDidMount() {
       

    }

    async buy_cart(){
        let payment = this.state.payment;
        let supply = this.state.supply;
        console.log("payment is "+payment+" , supply is "+supply+"\n");
        let response = await CartApi.buy_cart(payment, supply);
        alert(response.message);
    }
    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <h3>Shopping Cart</h3>
                        <form class="LoginForm" onSubmit={this.handleSubmit}>
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <List
                            sx={{
                                width: '100%',
                                maxWidth: 360,
                                bgcolor: 'background.paper',
                                position: 'relative',
                                overflow: 'auto',
                                maxHeight: 300,
                                '& ul': { padding: 0 },
                            }}
                            subheader={<li />}
                            >
                            {[0, 1, 2, 3, 4, 5, 6, ].map((item) => (
                                <ListItem key={`item}-${item}`}>
                                    <ListItemText primary={`Item ${item}`} />
                                </ListItem>
                                ))}
                            </List>
                            
                            <div className="ConnectRegister">
                                
                                {/* <Link to="/register">Create new account</Link> */}
                                <Button onClick={() => this.login()} variant="contained">Buy Cart </Button>
                                <Link href="/Register" underline="hover">
                                {'New user? Cretae new account'}
                                </Link>
                                {/* <input class="action" type="submit" value="Login"/> */}
                            </div>
                        </form>
                    </div>
                </main>
            );
        
    }
}