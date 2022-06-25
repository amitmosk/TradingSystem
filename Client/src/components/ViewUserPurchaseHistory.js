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


const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));



export default class UserPurchaseHistory extends Component {

    constructor(props) {
        super(props);
        this.state = {
            history: [],
            snackbar: null,
        };
        this.userApi = new UserApi();



    }
    async rate_store(rating) {
        console.log("in rate store");
        console.log("rating is = " + rating);
        let response = await this.storeApi.rate_store(this.state.store_id, rating);
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
                                    {this.state.history.map((his) => (
                                        <>
                                            <ListItem>
                                                {/* <CircleIcon fontSize='small' /> */}

                                                <ListItemText
                                                    primary={"Purchase ID: " + his.purchase_id}
                                                    secondary={'Total price: ₪ ' + his.total_price}
                                                />
                                                <BasicRating to_rate="Product" rating={this.rate_product.bind(this)} />
                                                <BasicRating to_rate="Store" rating={this.rate_store.bind(this)} />
                                            </ListItem>


                                        </>
                                        


                                    ))




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
