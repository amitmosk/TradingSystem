import * as React from 'react';
import { Component } from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';

import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ExpandLessIcon from '@mui/icons-material/ExpandLess';
import ListItemButton from '@mui/material/ListItemButton';
import CircleIcon from '@mui/icons-material/Circle';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import FolderIcon from '@mui/icons-material/Folder';
import DeleteIcon from '@mui/icons-material/Delete';
import HomeIcon from '@mui/icons-material/Home';
import Link from '@mui/material/Button';
import PersonIcon from '@mui/icons-material/Person';
import EditIcon from '@mui/icons-material/Edit';
import { User } from '../ServiceObjects/User';
import { StoreApi } from '../API/StoreApi';
import VisibilityIcon from '@mui/icons-material/Visibility';
import { UserApi } from '../API/UserApi';
import BasicRating from "./Rating";

// const staffy = [{ emaill1: "eylon@walla.com", emaill2: "eylon@eylon.eylon", type1: "typetype" },
// { emaill1: "eylon@walla.com", emaill2: "eylon@eylon.eylon", type1: "typetype" }];


const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));



export default class UserPurchaseHistory extends Component {

    constructor(props) {
        super(props);
        this.state = {
            history: [],
        };
        this.userApi = new UserApi();



    }
    async rate_store(rating) {
        console.log("in rate store");
        console.log("rating is = " + rating);
        let response = await this.storeApi.rate_store(this.state.store_id, rating);
        alert(response.message);
        if (!response.was_exception) {
          //get store
          //reload store
        } else {
        }
      }

    async view_user_purchase_history(values) {
        console.log("in view_user_purchase_history!\n");

        const response = await this.userApi.view_user_purchase_history();
        console.log(response);

        alert(response.message);
        if (!response.was_execption) {

            console.log(response.value);

            let res = [];

            this.setState({ history: response.value.historyList });
            console.log("history: ");
            console.log(this.state.history);

        }
        else {

        }
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

    async componentDidMount() {
        this.view_user_purchase_history();
    }

    render() {

        return (

            <>
                <Link href="/">
                    <HomeIcon></HomeIcon>
                </Link>
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
            </>
        );
        // )
        // );

    }
}
