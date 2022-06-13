import React, { Component } from "react";
import { Link } from "react-router-dom";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import HomeIcon from "@mui/icons-material/Home";
import Box from "@mui/material/Box";
import { StoreApi } from "../API/StoreApi";
import List from "@mui/material/List";
import MenuListComposition from "./MenuListComposition";
import { Paper } from "@mui/material";
import { Typography } from "@mui/material";
import BasicRating from "./Rating";
import Grid from "@mui/material/Grid";
import FormDialog from "./FormDialog";
import StoreProductsTable from "./StoreProductsTable";
import ProductionQuantityLimitsIcon from '@mui/icons-material/ProductionQuantityLimits';
import IconButton from "@mui/material/IconButton";

export default class StorePage extends Component {
  static displayName = StorePage.name;
  constructor(props) {
    super(props);
    this.state = {
      store_id: this.props.store_id,
      products: [],
      store_name:"", 
      founder_email: "",
      foundation_date:"",
      snackbar:null,
      send_question_to_store_fields: ["Enter your question"],
      ratings:[]
    };
    this.storeApi = new StoreApi();
    this.find_store_information(this.props.store_id);
    console.log("store id = "+this.props.store_id);
  }


  async find_store_information(store_id)
  {
    let store_res = await this.storeApi.find_store_information(this.props.store_id);
    let store = store_res.value;
    console.log("store foundation_date = "+store.foundation_date);
    // alert(store_res.message);
    
    if (!store_res.was_exception)
    {
      this.setState({ snackbar: { children: store_res.message, severity: "success" } });
      console.log("in find store info success");
      this.setState({

        store_name: store.name,
        founder_email: store.founder_email,
        foundation_date:store.foundation_date,
        // inventory : store.inventory,
        // storeReview: store.storeReview
      });
        const store_reviews_and_ratings = store.storeReview;
        const rating_entries = Object.entries(store_reviews_and_ratings.rating);
        const ratings = []
        rating_entries.map((e) => ratings.push("User - "+e[0]+" rate is "+e[1]))


      //Fetch product of the store
      let products_res = await this.storeApi.get_products_by_store_id(this.state.store_id);
      let products = products_res.value;
      // alert(products_res.message);
      if (!products_res.was_exception)
      {
        this.setState({ snackbar: { children: products_res.message, severity: "success" } });
        this.setState({
          products: products,
          ratings: ratings,
        });
      }
      else
      {
        this.setState({ snackbar: { children: products_res.message, severity: "error" } });
      }
      
    }
    else
    {
      this.setState({ snackbar: { children: store_res.message, severity: "error" } });
    
    }
    
  } 

  async componentDidMount() {

  }
  async send_question_to_store(values) {
    const store_id = this.props.store_id;
    const question = values[0];
    let response = await this.storeApi.send_question_to_store(store_id,question);
    if (!response.was_exception) {
      this.setState({ snackbar: { children: response.message, severity: "success" } });
    } 
    else {
      this.setState({ snackbar: { children: response.message, severity: "error" } });
    }
  }
  async rate_store(rating) {
    console.log("in rate store");
    console.log("rating is = " + rating);
    let response = await this.storeApi.rate_store(this.state.store_id, rating);
    // alert(response.message);
    this.setState({ snackbar: { children: response.message, severity: "success" } });

    if (!response.was_exception) {
      this.setState({ snackbar: { children: response.message, severity: "success" } });
      //get store
      //reload store
    } 
    else {
      this.setState({ snackbar: { children: response.message, severity: "error" } });
    }
  }

  render() {
    const ratings = this.state.ratings;
    const { redirectTo } = this.state;
    return (
        <div className="LoginWindow">
          <Grid
            container
            direction="column"
            justifyContent="center"
            alignItems="center"
          >
            {/* <h3>Store Name goes here</h3> */}
            <h1>{this.state.store_name}</h1>
            {/* <FormDialog fields={this.state.open_store_fields} getValues={this.open_store.bind(this)} name="Open Store"></FormDialog> */}
            {/* <FormDialog fields={this.state.send_question_to_store_fields} getValues={this.send_question_to_store.bind(this)} name="Send question to store"></FormDialog> */}
            <Paper>
              <Typography
                variant="body2"
                color="textPrimary"
                component="span"
                // style={{ width: "70%", margin: "auto" }} I think you should avoid break tags instead do something with the width
              >
                <div>
                  <h5>Founder Email: {this.state.founder_email}</h5>
                </div>
                <div>
                  <h5>Foundation Date: {this.state.foundation_date}</h5>
                </div>
                <div>
                  <h5 style={{ color: 'blue' }}>Ratings:</h5>
                </div>
                {ratings.length !== 0 ? (
                  <div>
                    <label>{this.state.ratings}</label>
                  </div>
                ) : <h5 style={{ color: 'red' }}> No Rating for this store</h5>}
              </Typography>
            </Paper>
            <h3>Products</h3>
          </Grid>
          <StoreProductsTable
            store_id={this.state.store_id}
          ></StoreProductsTable>
          <Link to={{pathname:`ViewBidsStatus` }}   underline="hover" >{"Show Store Bids"}</Link>
          <Box sx={{ "& > legend": { mt: 2 } }}></Box>
          <List
            sx={{
              width: "100%",
              maxWidth: 360,
              bgcolor: "background.paper",
              position: "relative",
              overflow: "auto",
              maxHeight: 300,
              "& ul": { padding: 0 },
            }}
            subheader={<li />}
          >
            {/* {this.state.products.map((product) => (
                                <ListItem key={`item}-${product}`}>
                                    <ListItemText primary={`Item ${product}`} />
                                </ListItem>
                                ))} */}
          </List>
          <Grid>
            <MenuListComposition
              item2={
                <FormDialog
                  fields={this.state.send_question_to_store_fields}
                  getValues={this.send_question_to_store.bind(this)}
                  name="Send question to store"
                ></FormDialog>
              }
              item3={
                <Link to={{pathname:`StoreManagment`}}   underline="hover" >{'Manage Store'}</Link> 
              }
            ></MenuListComposition>{" "}
          </Grid>
          <BasicRating to_rate="Store" rating={this.rate_store.bind(this)} />
              {!!this.state.snackbar && (
                <Snackbar
                  open
                  anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
                  onClose={this.handleCloseSnackbar}
                  autoHideDuration={1}
                >
                  <Alert
                    {...this.state.snackbar}
                    onClose={this.handleCloseSnackbar}
                  />
                </Snackbar>
              )}
        </div>
        
    );
  }
}

