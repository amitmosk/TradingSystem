import React, { Component } from "react";
import Link from "@mui/material/Button";
import { ConnectApi } from "../API/ConnectApi";
import { ProductApi } from "../API/ProductApi";
import { Container } from "@mui/material";
import Box from "@mui/material/Box";
import { AdminApi } from "../API/AdminApi";
import { StoreApi } from "../API/StoreApi";
import Grid from "@mui/material/Grid";
import FormDialog from "./FormDialog";
import ShoppingCart from "./ShoppingCart";
import { Row } from "react-grid-system";
import HomeProductsTable from "./HomeProductsTable";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";


export default class HomePage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "Guest",
      stores: [],
      open_store_fields: ["Store name"],
      send_question_to_admin_fields: ["Enter your question"],
      products: [],
    };
    this.connectAPI = new ConnectApi();
    this.productApi = new ProductApi();
    this.storeApi = new StoreApi();
    this.adminApi = new AdminApi();
    this.logout = this.logout.bind(this);
  }
  async componentDidMount() {
    console.log("in component did mount");
    this.setState({
      username: this.props.user_name,
    });
  }
  async open_store(values) {
    const store_name = values[0];
    let response = await this.storeApi.open_store(store_name);
    // alert(response.message);
    if (!response.was_exception) {
      this.setState({ snackbar: { children: response.message, severity: "success" } });

    } 
    else {
      this.setState({ snackbar: { children: response.message, severity: "error" } });

    }
  }

  async logout() {
    console.log("in logout home page");
    let response = await this.connectAPI.logout();
    // alert(response.message);
    if (!response.was_exception) {
      this.setState({ snackbar: { children: response.message, severity: "success" } });
      const user = response.value;
      this.props.updateUserState(user);
    }
    else
    {
      this.setState({ snackbar: { children: response.message, severity: "error" } });

    }
  }

  async send_question_to_admin(values) {
    const question = values[0];
    let response = await this.adminApi.send_question_to_admin(question);
    // alert(response.message);
    if (!response.was_exception) {
      this.setState({ snackbar: { children: response.message, severity: "success" } });
    } 
    else {
      this.setState({ snackbar: { children: response.message, severity: "error" } });

    }
  }
  show_products(products) {
    console.log("in show product");
    this.setState({
      products: products,
    });
    //    return (<ShoppingCart products={products}></ShoppingCart>);
    return <ShoppingCart products={this.state.products}></ShoppingCart>;
  }

  render() {
    return (
      <>
        <Container>
          <Box sx={{ flexGrow: 1 }}>
            <h1 className="Header" align="center">
              Welcome To Ebay
            </h1>
            {/* <HomePageSearch sx={{ height: '5%' }} /> */}
          </Box>
          <Grid
            container
            direction="row"
            justifyContent="center"
            alignItems="center"
          >
            <Row position="center">
              <HomeProductsTable />
            </Row>
          </Grid>
        </Container>
        <h1 style={{ color: "white" }}>-------------------------</h1>
        <h1 style={{ color: "white" }}>-------------------------</h1>
        <Grid container direction="row" justifyContent="space-evenly">
          <FormDialog
            fields={this.state.open_store_fields}
            getValues={this.open_store.bind(this)}
            name="Open Store"
          ></FormDialog>
          <FormDialog
            fields={this.state.send_question_to_admin_fields}
            getValues={this.send_question_to_admin.bind(this)}
            name="Send question to admin"
          ></FormDialog>
          <Link href="/AdminPage" underline="hover">
            {"Admin Operations"}
          </Link>
        </Grid>
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
      </>
    );
  }
}
