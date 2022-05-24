import React, { Component } from 'react';
import "./Login.css";
import Button from '@mui/material/Button';
// import Link from '@mui/material/Button';
// import Link from '@mui/material/Link';
import { Link } from "react-router-dom";
import HomeIcon from '@mui/icons-material/Home';
import { ConnectApi } from '../API/ConnectApi';
import Register from "./Register.js";
import HomePageSearch from './HomePageSearch';
import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import { withRouter } from 'react-router-dom';
import { Navigate } from 'react-router-dom';
import { AdminApi } from '../API/AdminApi';
import { TextField, makeStyles } from '@mui/material';
import { experimentalStyled as styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import FormDialog from './FormDialog';
import { StoreApi } from '../API/StoreApi';
import { Container, Row, Col } from 'react-grid-system';
import FormDialogPurchase from './FormDialogPurchase';
import { Input } from "@mui/material";

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));


const axios = require('axios');
const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";




export default class StoreManagment extends Component {
    static displayName = StoreManagment.name;

    constructor(props) {
        super(props);
        this.state = {
            storeidtoshowhistory: undefined,
            useremailtoshowhistory: undefined,
            useremailtoremove: undefined,
            storeidtoremove: undefined,
            add_product_fields: ["quantity", "name", "price", "category", "key_words"],
            arr: [],
            delete_product_fields: ["product_id"],
            purchase_policies_fields: ["policy"],
            discount_policies_fields: ["policy"],
            appoint_manager_fields: ["user_email_to_appoint"],
            appoint_owner_fields: ["user_email_to_appoint"],
            remove_owner_appointment_fields: ["user_email_to_appoint"],
            remove_manager_appointment_fields: ["user_email_to_appoint"],
            change_manager_permissions_fields: ["manager_email", "permissions"],
            user_massages_fields: [],
            // אילוצי עקביות
            close_store_temp_fields: [],
            open_closed_store_fields: [],
            store_id: this.props.store_id,

            option: "Simple",

        };
        this.adminApi = new AdminApi();
        this.storeApi = new StoreApi();
        this.handleInputChange = this.handleInputChange.bind(this);


    }


    handleInputChange(event) {
        const target = event.target;
        this.setState({
            [target.name]: target.value
        });
    }

    async componentDidMount() {
    }


    async add_product(values) {

        // console.log("in add product");
        // console.log(values[0]);
        // console.log(values[1]);
        // console.log(values[2]);
        // console.log(values[3]);
        // console.log(values[4]);
        // console.log(values[5]);
        // let response = await this.storeApi.add_product_to_store(values[0],values[1],values[2],values[3],values[4],values[5])
        /* add_product_fields: ["store_id", "quantity", "name", "price", "category", "key_words"] */
        console.log("in add product!\n");
        const store_id = this.state.store_id;
        const quantity = values[0];
        const name = values[1];
        const price = values[2];
        const category = values[3];
        const key_words = values[4];

        const response = await this.storeApi.add_product_to_store(store_id, quantity, name, price, category, key_words);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in add product - success!\n");
            //show history
        }
        else {

        }
    }
    async delete_product(values) {
        /*delete_product_fields: ["product_id", "store_id"],*/
        console.log("in delete product!\n");
        const product_id = values[0];
        const store_id = this.state.store_id;

        const response = await this.storeApi.delete_product_from_store(product_id, store_id);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in delete product - success!\n");
            //show history
        }
        else {

        }
    }
    async store_puchase_policies(values) {
        console.log("in store_puchase_policies!\n");
        console.log("in store_puchase_policies!\n");
        const store_id = this.state.store_id;
        const policy = values[0];

        const response = await this.storeApi.puchase_policies_fields(store_id, policy);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in store_puchase_policies - success!\n");
            //show history
        }
        else {

        }
    }
    async store_discount_policy(values) {
        console.log("in store_discount_policy!\n");
        const store_id = this.state.store_id;
        const policy = values[0];


        const response = await this.storeApi.set_store_discount_policy(store_id, policy);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in store_discount_policy - success!\n");
            //show history
        }
        else {

        }
    }
    async add_owner(values) {
        console.log("in add_owner!\n");
        const user_email_to_appoint = values[0];
        const store_id = this.state.store_id;

        const response = await this.storeApi.add_owner(user_email_to_appoint, store_id);
        alert(response.message);

        if (!response.was_execption) {
            console.log("in add_owner - success!\n");
            //show history
        }
        else {

        }
    }
    async delete_owner(values) {
        console.log("in delete_owner!\n");
        const user_email_to_delete_appointment = values[0];
        const store_id = this.state.store_id;

        const response = await this.storeApi.delete_owner(user_email_to_delete_appointment, store_id);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in delete_owner - success!\n");
            //show history
        }
        else {

        }
    }
    async add_manager(values) {
        console.log("in add_manager!\n");
        const user_email_to_appoint = values[0];
        const store_id = this.state.store_id;


        const response = await this.storeApi.add_manager(user_email_to_appoint, store_id);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in add_manager - success!\n");
            //show history
        }
        else {

        }
    }
    async delete_manager(values) {
        console.log("in delete_manager!\n");
        const user_email_to_delete_appointment = values[0];
        const store_id = this.state.store_id;

        console.log(store_id);


        const response = await this.storeApi.delete_manager(user_email_to_delete_appointment, store_id);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in delete_manager - success!\n");
            //show history
        }
        else {

        }
    }

    async close_store_temporarily(values) {
        console.log("in close_store_temporarily!\n");
        const store_id = this.state.store_id;
        const response = await this.storeApi.close_store_temporarily(store_id);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in close_store_temporarily - success!\n");
            //show history
        }
        else {

        }
    }

    async open_closed_store(values) {
        console.log("in open_close_store!\n");
        const store_id = this.state.store_id;
        const response = await this.storeApi.open_close_store(store_id);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in open_close_store - success!\n");
            //show history
        }
        else {

        }
    }

    async view_store_management_information(values) {
        console.log("in view_store_management_information!\n");
        const store_id = this.state.store_id;
        const response = await this.storeApi.view_store_management_information(store_id);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in view_store_management_information - success!\n");
            //show history
        }
        else {

        }
    }

    

    
    async view_store_purchases_history(values) {
        console.log("in view_store_purchases_history!\n");
        const store_id = this.state.store_id;
        const response = await this.storeApi.view_store_purchases_history(store_id);
        alert(response.message);
        if (!response.was_execption) {
            console.log("in view_store_purchases_history - success!\n");
            //show history
        }
        else {

        }
    }
    async edit_manager_permissions(values) {
        console.log("in edit_manager_permissions!\n");
        const manager_email = values[0];
        const store_id = this.state.store_id;
        const permissions = values[1];


        alert(response.message);
        const response = await this.storeApi.edit_manager_permissions(manager_email, store_id, permissions);
        if (!response.was_execption) {
            console.log("in edit_manager_permissions - success!\n");
            //show history
        }
        else {

        }
    }
    open_select_menu() {
        return (<FormDialogPurchase />)

    }


    async add_discount_rule(option) {
        console.log("in add discount rule");
        console.log(option);
        switch (option) {
            case "Simple":
                this.add_simple_discount_rule()
                break;
            case "Complex":
                this.add_complex_discount_rule()
                break;
            case "Composite Or":
                this.add_composite_or_discount_rule()
                break;
            case "Composite And":
                this.add_composite_and_discount_rule()
                break;
            case "Composite Xor":
                this.add_composite_xor_discount_rule()
                break;
            default:
                console.log("option is empty");
        }

    }
    async add_simple_discount_rule(option) {
    }
    async add_complex_discount_rule(option) {
    }
    async add_composite_or_discount_rule(option) {
    }
    async add_composite_and_discount_rule(option) {
    }
    async add_composite_xor_discount_rule(option) {
    }



    render() {
        const id=1;
        const { redirectTo } = this.state
        // { this.state.redirect ? (<Redirect push to="/"/>) : null }

        return (

            <Box sx={{ flexGrow: 1 }}>
                <Link to="/"><HomeIcon></HomeIcon></Link>
                <h3 align="center">Store Managment Page</h3>

                <Grid container spacing={6} paddingRight={25} paddingLeft={25} paddingTop={10}>
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.add_product_fields} getValues={this.add_product.bind(this)} name="Add Product"></FormDialog></Item>                    </Grid>
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.delete_product_fields} getValues={this.delete_product.bind(this)} name="Delete Product"></FormDialog></Item></Grid>
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.purchase_policies_fields} getValues={this.store_puchase_policies.bind(this)} name="Store Purchase Policies"></FormDialog></Item></Grid>
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.discount_policies_fields} getValues={this.store_discount_policy.bind(this)} name="Store Discount Policies"></FormDialog></Item></Grid >
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.appoint_manager_fields} getValues={this.add_manager.bind(this)} name="Add Manager"></FormDialog></Item></Grid >
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.appoint_owner_fields} getValues={this.add_owner.bind(this)} name="Add Owner"></FormDialog></Item></Grid >
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.remove_owner_appointment_fields} getValues={this.delete_owner.bind(this)} name="Delete Owner"></FormDialog></Item></Grid>
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.remove_manager_appointment_fields} getValues={this.delete_manager.bind(this)} name="Remove Manager"></FormDialog></Item ></Grid >
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.change_manager_permissions_fields} getValues={this.edit_manager_permissions.bind(this)} name="Change Manager Permissions"></FormDialog></Item ></Grid >
                    {/* <Grid item xs={3}>  <Item variant="outlined"> <Link to = {`ManagerViewStoreQuestions/${id}`} query={{store:1}}>Bid Item</Link></Item ></Grid >         */}
                    <Grid item xs={3}>  <Item variant="outlined"> <Link to={{pathname:`ManagerViewStoreQuestions/${id}`, state:{store_id:1} }}   underline="hover" >{'View User Questions'}</Link>   </Item ></Grid >
                    {/* <Grid item xs={3}>  <Item variant="outlined"> <Link href="/ViewStorePurchaseHistory"  underline="hover" >{'View Store Purchase History'}</Link>   </Item ></Grid > */}

                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.close_store_temp_fields} getValues={this.close_store_temporarily.bind(this)} name="Close Store Temporarily"></FormDialog></Item ></Grid >
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.open_closed_store_fields} getValues={this.open_closed_store.bind(this)} name="Open Closed Store"></FormDialog></Item ></Grid >

                    <Grid item xs={3}>  <Item variant="outlined"> <Link to="/ViewStaffInformation"  underline="hover" >{'View Staff Information'}</Link>   </Item ></Grid >
                    {/* <Grid item xs={3}>  <Item variant="outlined"> <Link href="/AddDiscount" underline="hover" >{'Add Discount Rule'}</Link></Item ></Grid> */}





                </Grid>

                <Box sx={{ flexGrow: 3 }}>

                </Box>
                <h3> </h3>
            </Box>




            // <main class="LoginMain">
            //     <div class="LoginWindow">
            //         <h3>Admin Page</h3>
            //         <form class="LoginForm" >
            //         <Button href="/Register" variant="contained">Send </Button>
            //         <Button href="/Register" variant="contained">Send </Button>
            //         <Button href="/Register" variant="contained">Send </Button>
            //         <Button href="/Register" variant="contained">Send </Button>
            //         <Button href="/Register" variant="contained">Send </Button>
            //         <Button href="/Register" variant="contained">Send </Button>
            //         </form>
            //     </div>
            // </main>
        );

    }

}