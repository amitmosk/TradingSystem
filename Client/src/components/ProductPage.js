import React, { Component } from 'react';
import { StoreApi } from '../API/StoreApi';
import { Paper } from '@mui/material';
import { Typography } from '@mui/material';
import BasicRating from './Rating';
import { ProductApi } from '../API/ProductApi';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 

  
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
            reviews:[],
            ratings:[],
            add_product_review_fields:["Review"],
            snackbar: null,
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
      // alert(product_res.message);
      if (!product_res.was_exception)
      {
        console.log(product_res.value);
        this.setState({ snackbar: { children: product_res.message, severity: "success" } });
        let product = product_res.value;
        const product_reviews_and_ratings = product.productReview;
        const rating_entries = Object.entries(product_reviews_and_ratings.rating);
        const ratings = []
        rating_entries.map((e) => ratings.push("User - "+e[0]+" rate is "+e[1]))


        const reviews_entries = Object.entries(product_reviews_and_ratings.reviews);
        const reviews = []
        reviews_entries.map((e) => reviews.push("User - "+e[0]+" review is "+e[1]))
        this.setState({
          name: product.name,
          category:product.category,
          key_words:product.key_words,
          price:product.original_price,
          reviews:reviews,
          ratings:ratings,
    });

      }
      else
      {
        this.setState({ snackbar: { children: product_res.message, severity: "error" } });
      }
      


      
      
 
    }
    
  async rate_product(rating) {
    console.log("in rate Product");
    console.log("rating is = "+rating);
    let response = await this.productApi.rate_product(this.state.product_id, this.state.store_id,rating);
    // alert(response.message);
    if (!response.was_exception)
    {
      this.setState({ snackbar: { children: response.message, severity: "success" } });
      //get product
      //reload product
      
    }
    else
    {
      this.setState({ snackbar: { children: response.message, severity: "error" } });

    }
    
}





async find_product_by_category(val) {
    console.log("in find_product_by_category");
    let response = await this.productApi.find_product_by_category(val);
    // alert(response.message);
        if (!response.was_exception) {
            console.log("in find_product_by_category- success");
            this.setState({ snackbar: { children: response.message, severity: "success" } });
        }
        else {
            console.log("in find_product_by_category - fail");
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }
    
}

















    
    render() {
      const ratings = this.state.ratings;
      const reviews = this.state.reviews;
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
                        <h5 style={{ color: 'blue' }}>Ratings</h5>
                        {ratings.length !== 0 ? <div> {ratings.map((r)=><div>{r}</div>)}</div>: <h5 style={{ color: 'red' }}> No Rating for this product</h5>} 
                        <h5 style={{ color: 'blue' }}>Reviews</h5>
                        {reviews.length !== 0 ? <div> {reviews.map((r)=><div>{r}</div>)}</div>: <h5 style={{ color: 'red' }}> No Reviews for this product</h5>} 

              </Typography>
            </Paper>
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
                            
                            {/* <BasicRating to_rate="Product" rating={this.rate_product.bind(this)} /> */}
                             
                       
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