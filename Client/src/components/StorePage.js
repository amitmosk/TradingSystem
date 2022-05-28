import React, { Component } from "react";
import Button from "@mui/material/Button";
// import Link from "@mui/material/Button";
import { Link } from "react-router-dom";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import HomeIcon from "@mui/icons-material/Home";
import { ConnectApi } from "../API/ConnectApi";
import Register from "./Register.js";
import Box from "@mui/material/Box";
import ImageListItem from "@mui/material/Box";
import ImageList from "@mui/material/Box";
import { StoreApi } from "../API/StoreApi";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import ListSubheader from "@mui/material/ListSubheader";
import { CartApi } from "../API/CartApi";
import MenuListComposition from "./MenuListComposition";
import { Container, Row, Col } from "react-grid-system";
import { Paper } from "@mui/material";
import { Typography } from "@mui/material";
import Rating from "@mui/material/Rating";
import BasicRating from "./Rating";
import ShoppingCart from "./ShoppingCart";
import { Store, ThirtyFpsRounded } from "@mui/icons-material";
import Grid from "@mui/material/Grid";
import FormDialog from "./FormDialog";
import StoreProductsTable from "./StoreProductsTable";

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
      // inventory : "",
      // storeReview: "",
      send_question_to_store_fields: ["Enter your question"],
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
    this.setState({ snackbar: { children: store_res.message, severity: "success" } });
    if (!store_res.was_exception)
    {
      console.log("in fnid store info success");
      this.setState({

        store_name: store.name,
        founder_email: store.founder_email,
        foundation_date:store.foundation_date,
        // inventory : store.inventory,
        // storeReview: store.storeReview
      });


    }
    else
    {
      

    }
    let products_res = await this.storeApi.get_products_by_store_id(this.state.store_id);
    let products = products_res.value;
    // alert(products_res.message);
    this.setState({ snackbar: { children: products_res.message, severity: "success" } });

    // products.map((p)=>)
    this.setState({
      products: products,
    });
  
    

  } 

  // async componentDidMount() {
  //   let store_res = await this.storeApi.find_store_information(this.props.store_id);
  //   let store = store_res.value;
  //   console.log("store foundation_date = "+store.foundation_date);
  //   alert(store_res.message);
  //   if (!store_res.was_exception)
  //   {
  //     console.log("in fnid store info success");
  //     this.setState({

  //       store_name: store.name,
  //       founder_email: store.founder_email,
  //       foundation_date:store.foundation_date,
  //       // inventory : store.inventory,
  //       // storeReview: store.storeReview
  //     });


  //   }
  //   else
  //   {
      

  //   }
    
    

  //   // let products_res = await this.storeApi.get_products_by_store_id(this.state.store_id);
  //   // let products = products_res.value;
  //   // // products.map((p)=>)
  //   // this.setState({
  //   //   products: products,
  //   // });
  // }
  async send_question_to_store(values) {
    const store_id = this.props.store_id;
    const question = values[0];
    let response = await this.storeApi.send_question_to_store(
      store_id,
      question
    );
    if (!response.was_exception) {
    } else {
    }
  }
  async rate_store(rating) {
    console.log("in rate store");
    console.log("rating is = " + rating);
    let response = await this.storeApi.rate_store(this.state.store_id, rating);
    // alert(response.message);
    this.setState({ snackbar: { children: response.message, severity: "success" } });

    if (!response.was_exception) {
      //get store
      //reload store
    } else {
    }
  }
  // async send_question_to_store(values) {
  //   const question = values[0];
  //   let response = await this.storeApi.send_question_to_store(question);
  //   alert(response.message);
  //   if (!response.was_exception)
  //   {

  //   }
  //   else{

  //   }
  // }

  render() {
    const { redirectTo } = this.state;
    return (
        <div className="LoginWindow">
          <Grid
            container
            direction="column"
            justifyContent="center"
            alignItems="center"
          >
            <Link to="/">
              <HomeIcon></HomeIcon>
            </Link>
            {/* <h3>Store Name goes here</h3> */}
            <h3>{this.state.store_name}</h3>
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
                {/* {this.state.storeReview !="" ? (
                  <div>
                    <label>{this.state.storeReview}</label>
                  </div>
                ) : null} */}
              </Typography>
            </Paper>
            <h5>Products</h5>
          </Grid>
          <StoreProductsTable
            store_id={this.state.store_id}
          ></StoreProductsTable>
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
                  autoHideDuration={6000}
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

{
  /* <Typography component="legend">Controlled</Typography>
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
      <Rating name="no-value" value={null} /> */
}
