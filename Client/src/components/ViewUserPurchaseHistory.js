import * as React from 'react';
import { Component } from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';

import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Grid from '@mui/material/Grid';
import HomeIcon from '@mui/icons-material/Home';
import Link from '@mui/material/Button';
import { UserApi } from '../API/UserApi';
import BasicRating from "./Rating";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import { ProductApi } from '../API/ProductApi';
import { StoreApi } from '../API/StoreApi';


const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));



export default class UserPurchaseHistory extends Component {

    constructor(props) {
        super(props);
        this.state = {
            product_id:undefined,
            store_id:undefined,
            history: [],
            snackbar: null,
        };
        this.userApi = new UserApi();
        this.productApi = new ProductApi();
        this.storeApi= new StoreApi();



    }
    async rate_store(rating, store_id, product_id) {
        console.log("in rate store");
        console.log("rating is = " + rating);
        console.log("store_id is = " + store_id);
        let response = await this.storeApi.rate_store(parseInt(store_id), rating);
        // alert(response.message);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
          //get store
          //reload store
        } else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });
        }
      }

    async view_user_purchase_history(values) {
        console.log("in view_user_purchase_history!\n");

        const response = await this.userApi.view_user_purchase_history();
        console.log(response);

        // alert(response.message);
        if (!response.was_exception) {
            if (response.message == "The system is not available right now, come back later")
                this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log(response.value);
            this.setState({ history: response.value.historyList });
            console.log("history: ");
            console.log(this.state.history);

        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });

        }
    }
    async rate_product(rating, store_id, product_id) {
        console.log("in rate Product");
        console.log("rating is = "+rating);
        console.log("store_id is = "+store_id);
        console.log("product_id is = "+product_id);
        let response = await this.productApi.rate_product(parseInt(product_id), parseInt(store_id),rating);
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

    async componentDidMount() {
        this.view_user_purchase_history();
    }

    render() {

        return (

            <>

                <Box position='center' align='center'>
                    <Grid position='center' row-spacing={3}>
                        <Grid item>
                            <h3 class="Header" align="center">
                                Purchase History
                            </h3>
                        </Grid>

                        <Grid position='center' align='center'>

                            <Demo>
                                <List>
                                <h5 class="Header" align="center">
                                </h5>
                                    {this.state.history.length !==0 ?  this.state.history.map((his) => (
                                        <>
                                            <ListItem>
                                                <ListItemText
                                                    primary={<h3>Purchase ID: {his.user_purchase_id} , Total price: {his.total_price} $</h3>}
                                                    secondary={
                                                        <ListItemText
                                                        primary={Object.keys(his.store_id_purchase).map((s)=>
                                                            <ListItemText
                                                            primary={<h4>store id: {s} Total price: {his.store_id_purchase[s].totalPrice} $</h4> }
                                                            // secondary={<BasicRating to_rate="Store" rating={this.rate_store.bind(this)} />}
                                                            secondary={Object.keys(his.store_id_purchase[s].product_and_quantity).map((p)=>
                                                                <ListItemText
                                                                primary={`Product id: ${p} Quantity: ${his.store_id_purchase[s].product_and_quantity[p]} at ${his.store_id_purchase[s].transaction_date}`}
                                                                secondary={<BasicRating to_rate="Product" params={[s,p]} rating={this.rate_product.bind(this)} color={"blue"}/>}
                                                                >
                                                                </ListItemText>

                                                                )}

                                                            >
                                                            </ListItemText>

                                                        )
                                                        }

                                                        secondary={<BasicRating to_rate="Store" params={["1", "-1"]} rating={this.rate_store.bind(this) } color={"red"}/>}

                                                        >


                                                        </ListItemText>
                                                    }

                                                />


                                            </ListItem>


                                        </>
                                        


                                    )) : <h3 style={{ color: 'red' }}>No History To Show</h3>




                                    }
                                </List>
                                
                            </Demo>
                        </Grid>
                    </Grid>
                </Box>
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
