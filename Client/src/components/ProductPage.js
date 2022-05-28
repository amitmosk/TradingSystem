import React, { Component } from 'react';
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
import BasicRating from './Rating';
import ShoppingCart from './ShoppingCart';
import { ThirtyFpsRounded } from '@mui/icons-material';
import Grid from '@mui/material/Grid';
import FormDialog from './FormDialog';
import { Input } from "@mui/material";
import { ProductApi } from '../API/ProductApi';
import Card from '@mui/material/Card';

  
export default class ProductPage extends Component {
    static displayName = ProductPage.name;
    constructor(props) {
        super(props);
        this.state = { 
            product_id:this.props.product_id,
            store_id:this.props.store_id,
            name: undefined,
            category:undefined,
            key_words:undefined,
            price:undefined,
            productReview:undefined,
            add_product_review_fields:["Review"],
        };
        this.storeApi = new StoreApi();
        this.productApi = new ProductApi();
        this.handleInputChange = this.handleInputChange.bind(this);
        console.log("in product page, product id = "+this.state.product_id+" store id = "+this.state.store_id);

    } 
    handleInputChange(event) {
        const target = event.target;
        console.log(target.name);
        console.log(target.value);
        this.setState({
            [target.name]: target.value
        });
    }   

    
    
    
    async componentDidMount() {
        console.log("product id = "+this.state.product_id);
      let product_res =  await this.productApi.find_product_information(this.state.product_id, this.state.store_id) ;
      let product = product_res.value;
      console.log("response = "+product_res);
        alert(product_res.message);
        
      this.setState({
        name: product.name,
        category:product.category,
        key_words:product.key_words,
        price:product.price,
        productReview:product.productReview,
    });
    console.log("review ="+this.productReview);
    console.log("in product page - component did mount");
      
 
    }
    
  async rate_product(rating) {
    console.log("in rate Product");
    console.log("rating is = "+rating);
    let response = await this.productApi.rate_product(this.state.product_id, this.state.store_id,rating);
    alert(response.message);
    if (!response.was_exception)
    {
      //get product
      //reload product
      
    }
    else
    {

    }
    
}





async find_product_by_category(val) {
    console.log("in find_product_by_category");
    let response = await this.productApi.find_product_by_category(val);
    alert(response.message);
        if (!response.was_exception) {
            console.log("in find_product_by_category- success");
        }
        else {
            console.log("in find_product_by_category - fail");
        }
    
}

















    
    render() {
        const {redirectTo} = this.state
            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                        {/* <row><h3>Product Name goes here</h3></row> */}
                        <row><h3>{this.state.name}</h3></row>  
                        <Paper >
              
             <Typography
                // style={{ width: "70%", margin: "auto" }} I think you should avoid break tags instead do something with the width
                variant="body2"
                color="textPrimary"
                component="span"
              >
                 {/* <div> Product info goes here</div> */}
                        {/* <div> Product info goes here</div>
                        <div> Product info goes here</div>
                        <div> Product info goes here</div>
                        <div> Product info goes here</div> */}
                        
                        <div> Category: {this.state.category}</div>
                        <div> Price: {this.state.price}</div>
                        {/* {this.state.productReview ? <div>{this.state.productReview}</div> : null} */}

              </Typography>
            </Paper>
                      
                           
                        


                        
                        

                           
                            
                            <BasicRating to_rate="Product" rating={this.rate_product.bind(this)} />
                            
                            
    

                         

                            


                     

                                   
                            
                       
                    </div>
                </main>
            );
        
    }
}












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