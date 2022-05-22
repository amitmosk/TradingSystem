import React, { Component } from 'react';
import "./Login.css";
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
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
import { CartApi } from '../API/CartApi';
import MenuListComposition from './MenuListComposition';
import { Container, Row, Col } from 'react-grid-system';
import { Paper } from '@mui/material';
import { Typography } from '@mui/material';
import Rating from '@mui/material/Rating';
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
            products:[],
            founder_email :undefined,
        store_name :undefined ,
        foundation_date : undefined,
        storeReviewInformation : undefined,
        send_question_to_store_fields : ["Enter your question"],
        };
        this.storeApi = new StoreApi();
        const value = undefined;
        const setValue = undefined;
       
    }    

    
    
    
    async componentDidMount() {
      let store_res =  await StoreApi.find_store_information(this.props.store_id) ;
      let store = store_res.value;
      this.setState({
        founder_email :store.founder_email,
        store_name : store.store_name,
        foundation_date : store.foundation_date,
        storeReviewInformation : store.storeReviewInformation,
    });
      

        let products_res = await this.storeApi.get_products_by_store_id(this.props.store_id);
        let products = products_res.value;
        this.setState({
          products:products
        });
        

        
    }
    async send_question_to_store(values) {
      const store_id = this.props.store_id;
      const question = values[0];
      let response = await this.storeApi.send_question_to_store(store_id, question);
      if (!response.was_exception)
      {

      }
      else
      {

      }
  }


    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                    <Link href="/"><HomeIcon></HomeIcon></Link>
                        <h3>Store Name goes here</h3>
                       {/* <FormDialog fields={this.state.open_store_fields} getValues={this.open_store.bind(this)} name="Open Store"></FormDialog> */}
                            {/* <FormDialog fields={this.state.send_question_to_store_fields} getValues={this.send_question_to_store.bind(this)} name="Send question to store"></FormDialog> */}

                        <Col><row><MenuListComposition item2="Send Complaint" item3={ <Link href="/StoreManagment" underline="hover" >
                                {'Manage Store'}
                                </Link>}>
                                </MenuListComposition></row> </Col>
                        {/* <h3>{this.state.store_name}</h3>  */}

                        {/* <div> {this.state.founder_email}</div>
                        <div> {this.state.foundation_date}</div>
                        <div> {this.state.storeReview}</div> */}


                        {/* <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        <div> Store info goes here</div> */}
                        
                        <Paper >
             <Typography
                // style={{ width: "70%", margin: "auto" }} I think you should avoid break tags instead do something with the width
                variant="body2"
                color="textPrimary"
                component="span"
              >
                 <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        <div> Store info goes here</div>
                        <div> Store info goes here</div>
                
              </Typography>
            </Paper>
                        {/* <Box
                            sx={{
                                width: 300,
                                height: 300,
                                backgroundColor: 'primary.dark',
                                '&:hover': {
                                backgroundColor: 'primary.main',
                                opacity: [0.9, 0.8, 0.7],
                                content:'"ffffkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkffffffffffffffff"',
                                },
                                
                            }}
                            
                            /> */}
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
                            {[0, 1, 2, 3, 4, 5, 6, ].map((item) => (
                                <ListItem key={`item}-${item}`}>
                                    <ListItemText primary={`Item ${item}`} />
                                </ListItem>
                                ))}
                            </List>

                            <Box
      sx={{
        '& > legend': { mt: 2 },
      }}
    >
      {/* <Typography component="legend">Controlled</Typography>
      <Rating
        name="simple-controlled"
        value={value}
        onChange={(event, newValue) => {
          setValue(newValue);
        }}
      />
      <Typography component="legend">Read only</Typography>
      <Rating name="read-only" value={value} readOnly />
      <Typography component="legend">Disabled</Typography>
      <Rating name="disabled" value={value} disabled />
      <Typography component="legend">No rating given</Typography>
      <Rating name="no-value" value={null} /> */}
    </Box>
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
                            {this.state.products.map((product) => (
                                <ListItem key={`item}-${product}`}>
                                    <ListItemText primary={`Item ${product}`} />
                                </ListItem>
                                ))}
                            </List>


                     

                                   
                            
                       
                    </div>
                </main>
            );
        
    }
}












