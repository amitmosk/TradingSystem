import React, { Component } from 'react';
import { Link } from "react-router-dom";
import HomeIcon from '@mui/icons-material/Home';
import { AdminApi } from '../API/AdminApi';
import { experimentalStyled as styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import FormDialog from './FormDialog';
import { StoreApi } from '../API/StoreApi';
import StoreManagmentProductsTable from './StoreManagmentProductsTable';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));



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
            answer_user_questions_fields: ["question_id", "answer"],
            user_massages_fields: [],
            store_purchase_history_fields: [],
            // אילוצי עקביות
            close_store_temp_fields: [],
            open_closed_store_fields: [],
            staff_information_fields: [],
            store_id: this.props.store_id,

            option: "Simple",
            snackbar: null,

        };
      
        this.adminApi = new AdminApi();
        this.storeApi = new StoreApi();
        this.handleInputChange = this.handleInputChange.bind(this);
        console.log("in store managemrnt , store page = "+this.props.store_id);


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
        console.log("in add product!\n");
        const store_id = this.state.store_id;
        const quantity = values[0];
        const name = values[1];
        const price = values[2];
        const category = values[3];
        const key_words = values[4];

        const response = await this.storeApi.add_product_to_store(store_id, quantity, name, price, category, key_words);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in add product - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async delete_product(values) {
        /*delete_product_fields: ["product_id", "store_id"],*/
        console.log("in delete product!\n");
        const product_id = values[0];
        const store_id = this.state.store_id;

        const response = await this.storeApi.delete_product_from_store(product_id, store_id);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in delete product - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async store_purchase_policies(values) {
        console.log("in store_purchase_policies!\n");
        console.log("in store_purchase_policies!\n");
        const store_id = this.state.store_id;
        const policy = values[0];

        const response = await this.storeApi.puchase_policies_fields(store_id, policy);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in store_purchase_policies - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async store_discount_policy(values) {
        console.log("in store_discount_policy!\n");
        const store_id = this.state.store_id;
        const policy = values[0];


        const response = await this.storeApi.set_store_discount_policy(store_id, policy);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in store_discount_policy - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async add_owner(values) {
        console.log("in add_owner!\n");
        const user_email_to_appoint = values[0];
        const store_id = this.state.store_id;

        const response = await this.storeApi.add_owner(user_email_to_appoint, store_id);
        // alert(response.message);

        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in add_owner - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async delete_owner(values) {
        console.log("in delete_owner!\n");
        const user_email_to_delete_appointment = values[0];
        const store_id = this.state.store_id;

        const response = await this.storeApi.delete_owner(user_email_to_delete_appointment, store_id);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in delete_owner - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async add_manager(values) {
        console.log("in add_manager!\n");
        const user_email_to_appoint = values[0];
        const store_id = this.state.store_id;


        const response = await this.storeApi.add_manager(user_email_to_appoint, store_id);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in add_manager - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async delete_manager(values) {
        console.log("in delete_manager!\n");
        const user_email_to_delete_appointment = values[0];
        const store_id = this.state.store_id;

        console.log(store_id);


        const response = await this.storeApi.delete_manager(user_email_to_delete_appointment, store_id);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in delete_manager - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }

    async close_store_temporarily(values) {
        console.log("in close_store_temporarily!\n");
        const store_id = this.state.store_id;
        const response = await this.storeApi.close_store_temporarily(store_id);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in close_store_temporarily - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }

    async open_closed_store(values) {
        console.log("in open_close_store!\n");
        const store_id = this.state.store_id;
        const response = await this.storeApi.open_close_store(store_id);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in open_close_store - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }

    async view_store_management_information(values) {
        console.log("in view_store_management_information!\n");
        const store_id = this.state.store_id;
        const response = await this.storeApi.view_store_management_information(store_id);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in view_store_management_information - success!\n");
            return response.value;
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }

    async manager_view_store_questions(values) {
        console.log("in manager_view_store_questions!\n");
        const store_id = this.state.store_id;
        const response = await this.storeApi.manager_view_store_questions(store_id);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in manager_view_store_questions - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }

    async manager_answer_question(values) {
        console.log("in manager_answer_question!\n");
        const store_id = this.state.store_id;
        const question_id = values[0];
        const answer = values[1];


        const response = await this.storeApi.manager_answer_question(store_id, question_id, answer);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in manager_answer_question - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async view_store_purchases_history(values) {
        console.log("in view_store_purchases_history!\n");
        const store_id = this.state.store_id;
        const response = await this.storeApi.view_store_purchases_history(store_id);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in view_store_purchases_history - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async edit_manager_permissions(values) {
        console.log("in edit_manager_permissions!\n");
        const manager_email = values[0];
        const store_id = this.state.store_id;
        const permissions = values[1];


        const response = await this.storeApi.edit_manager_permissions(manager_email, store_id, permissions);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in edit_manager_permissions - success!\n");
            //show history
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
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
                
                <h3 align="center">Store Managment Page</h3>
                <Grid>
                    <StoreManagmentProductsTable store_id={this.state.store_id}></StoreManagmentProductsTable>
                </Grid>
                <Grid container spacing={6} paddingRight={25} paddingLeft={25} paddingTop={10}>
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.add_product_fields} getValues={this.add_product.bind(this)} name="Add Product"></FormDialog></Item>                    </Grid>
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.delete_product_fields} getValues={this.delete_product.bind(this)} name="Delete Product"></FormDialog></Item></Grid>
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.purchase_policies_fields} getValues={this.store_purchase_policies.bind(this)} name="Store Purchase Policies"></FormDialog></Item></Grid>
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.discount_policies_fields} getValues={this.store_discount_policy.bind(this)} name="Store Discount Policies"></FormDialog></Item></Grid >
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.appoint_manager_fields} getValues={this.add_manager.bind(this)} name="Add Manager"></FormDialog></Item></Grid >
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.appoint_owner_fields} getValues={this.add_owner.bind(this)} name="Add Owner"></FormDialog></Item></Grid >
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.remove_owner_appointment_fields} getValues={this.delete_owner.bind(this)} name="Delete Owner"></FormDialog></Item></Grid>
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.remove_manager_appointment_fields} getValues={this.delete_manager.bind(this)} name="Remove Manager"></FormDialog></Item ></Grid >
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.change_manager_permissions_fields} getValues={this.edit_manager_permissions.bind(this)} name="Change Manager Permissions"></FormDialog></Item ></Grid >
                    {/* <Grid item xs={3}>  <Item variant="outlined"> <Link to = {`ManagerViewStoreQuestions/${id}`} query={{store:1}}>Bid Item</Link></Item ></Grid >         */}
                    <Grid item xs={3}>  <Item variant="outlined"> <Link to={{pathname:`ManagerViewStoreQuestions` }}   underline="hover" >{'View User Questions'}</Link>   </Item ></Grid >
                    {/* <Grid item xs={3}>  <Item variant="outlined"> <Link href="/ViewStorePurchaseHistory"  underline="hover" >{'View Store Purchase History'}</Link>   </Item ></Grid > */}

                    {/* <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.close_store_temp_fields} getValues={this.close_store_temporarily.bind(this)} name="Close Store Temporarily"></FormDialog></Item ></Grid > */}
                    {/* <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.open_closed_store_fields} getValues={this.open_closed_store.bind(this)} name="Open Closed Store"></FormDialog></Item ></Grid > */}
                    {/* <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.user_massages_fields} getValues={this.manager_view_store_questions.bind(this)} name="View User Questions"></FormDialog></Item ></Grid > */}

                    <Grid item xs={3}>  <Item variant="outlined"> <Link to={{pathname:`ViewStorePurchaseHistory` }} underline="hover" >{'View Store Purchase History'}</Link></Item ></Grid>

                    {/* <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.store_purchase_history_fields} getValues={this.view_store_purchases_history.bind(this)} name="View Store Purchase History"></FormDialog></Item ></Grid > */}
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.close_store_temp_fields} getValues={this.close_store_temporarily.bind(this)} name="Close Store Temporarily" title="Confirm to Close Store Temporarily" submit_button="Confirm"></FormDialog></Item ></Grid >
                    <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.open_closed_store_fields} getValues={this.open_closed_store.bind(this)} name="Open Closed Store" title="Confirm to Open Closed Store" submit_button="Confirm"></FormDialog></Item ></Grid >
                    {/* <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.staff_information_fields} getValues={this.view_store_management_information.bind(this)} name="View Staff Information"></FormDialog></Item ></Grid > */}
                    <Grid item xs={3}>  <Item variant="outlined"> <Link outlinedVar="text" to={{pathname:`ViewStaffInformation` }} >{'View Staff Information'}</Link></Item ></Grid >

                    {/* <Grid item xs={3}>  <Item variant="outlined"> <FormDialog outlinedVar="text" fields={this.state.answer_user_questions_fields} getValues={this.manager_answer_question.bind(this)} name="Answer Users Questions"></FormDialog></Item ></Grid> */}
                    <Grid item xs={3}>  <Item variant="outlined"> <Link to={{pathname:`AddDiscount` }} underline="hover" >{'Add Discount Rule'}</Link></Item ></Grid>





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
                <h3> </h3>
            </Box>


        );

    }

}