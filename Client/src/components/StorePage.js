import React, { Component } from 'react';
import "./Login.css";
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import { ConnectApi } from '../API/ConnectApi';
import Register from "./Register.js";
import Box from '@mui/material/Box';
import ImageListItem from '@mui/material/Box';
import ImageList from '@mui/material/Box';
import { StoreApi } from '../API/StoreApi';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';
 
// import itemData from '@mui/material/Box';

const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";
const itemData = [
    {
      img: 'https://images.unsplash.com/photo-1551963831-b3b1ca40c98e',
      title: 'Breakfast',
    },
    {
      img: 'https://images.unsplash.com/photo-1551782450-a2132b4ba21d',
      title: 'Burger',
    },
    {
      img: 'https://images.unsplash.com/photo-1522770179533-24471fcdba45',
      title: 'Camera',
    },
    {
      img: 'https://images.unsplash.com/photo-1444418776041-9c7e33cc5a9c',
      title: 'Coffee',
    },
    {
      img: 'https://images.unsplash.com/photo-1533827432537-70133748f5c8',
      title: 'Hats',
    },
    {
      img: 'https://images.unsplash.com/photo-1558642452-9d2a7deb7f62',
      title: 'Honey',
    },
    {
      img: 'https://images.unsplash.com/photo-1516802273409-68526ee1bdd6',
      title: 'Basketball',
    },
    {
      img: 'https://images.unsplash.com/photo-1518756131217-31eb79b20e8f',
      title: 'Fern',
    },
    {
      img: 'https://images.unsplash.com/photo-1597645587822-e99fa5d45d25',
      title: 'Mushrooms',
    },
    {
      img: 'https://images.unsplash.com/photo-1567306301408-9b74779a11af',
      title: 'Tomato basil',
    },
    {
      img: 'https://images.unsplash.com/photo-1471357674240-e1a485acb3e1',
      title: 'Sea star',
    },
    {
      img: 'https://images.unsplash.com/photo-1589118949245-7d38baf380d6',
      title: 'Bike',
    },
  ];
export default class StorePage extends Component {
    static displayName = StorePage.name;

    constructor(props) {
        super(props);
        this.state = { 
            stroe_id: undefined,
            // founder_email: store.founder_email,
            // store_name: store.store_name,
            // foundation_date: store.foundation_date,
            // storeReview: store.storeReview
        };
       
    }
    
    
    
    async componentDidMount() {
        let response = await StoreApi.find_store_information(this.state.store_id);
        let store = response.value;
        this.setState({
            founder_email: store.founder_email,
            store_name: store.store_name,
            foundation_date: store.foundation_date,
            storeReview: store.storeReview
        })

        
    }


    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                        <h3>Store Name goes here</h3> 
                        <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        
                        
                        <Box
                            sx={{
                                width: 300,
                                height: 300,
                                backgroundColor: 'primary.dark',
                                '&:hover': {
                                backgroundColor: 'primary.main',
                                opacity: [0.9, 0.8, 0.7],
                                },
                                
                            }}
                            
                            />
                            <h3>Products</h3>
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
                            {[0, 1, 2, 3, 4].map((sectionId) => (
                                <li key={`section-${sectionId}`}>
                                <ul>
                                    
                                    {[0, 1, 2].map((item) => (
                                    <ListItem key={`item-${sectionId}-${item}`}>
                                        <ListItemText primary={`Item ${item}`} />
                                    </ListItem>
                                    ))}
                                </ul>
                                </li>
                            ))}
                            </List>        
                            {/* <ImageList sx={{ width: 1000, height: 1450 }} cols={3} rowHeight={164}>
                            {itemData.map((item) => (
                            <ImageListItem key={item.img}>
                                <img
                                src={`${item.img}?w=164&h=164&fit=crop&auto=format`}
                                srcSet={`${item.img}?w=164&h=164&fit=crop&auto=format&dpr=2 2x`}
                                alt={item.title}
                                loading="lazy"
                                />
                            </ImageListItem>
                            ))}
                            </ImageList> */}
                        {/* <form class="LoginForm" onSubmit={this.handleSubmit}>
                            {this.state.loginError ?
                                <div class="CenterItemContainer"><label>{this.state.loginError}</label></div> : null}
                            <input type="text" name="email" value={this.state.email}
                                    placeholder="Email" required/>
                            <input type="password" name="password" value={this.state.password}
                                    placeholder="Password" required/>
                            
                            <div className="ConnectRegister">
                                
                                <Button onClick={() => this.login()} variant="contained">Login </Button>
                                <Link href="/Register" underline="hover">
                                {'New user? Cretae new account'}
                                </Link>
                            </div>
                        </form> */}
                    </div>
                </main>
            );
        
    }
}












