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

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import FolderIcon from '@mui/icons-material/Folder';
import DeleteIcon from '@mui/icons-material/Delete';
import HomeIcon from '@mui/icons-material/Home';
import Link from '@mui/material/Button';
import PersonIcon from '@mui/icons-material/Person';
import EditIcon from '@mui/icons-material/Edit';
import { Store } from '../ServiceObjects/User';
import { StoreApi } from '../API/StoreApi';
import VisibilityIcon from '@mui/icons-material/Visibility';

// const staffy = [{ emaill1: "eylon@walla.com", emaill2: "eylon@eylon.eylon", type1: "typetype" },
// { emaill1: "eylon@walla.com", emaill2: "eylon@eylon.eylon", type1: "typetype" }];


const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));



export default class ViewStorePurchaseHistory extends Component {

    constructor(props) {
        super(props);
        this.state = {
            store_id : this.props.store_id,
            history: [],
            open: false,
        };
        this.storeApi = new StoreApi();



    }
    handleClick = () => {
        this.setState({ open: !this.state.open });
    };

    async view_store_purchases_history(values) {
        console.log("in view_store_purchases_history!\n");
        // const store_id = this.state.store_id;
        const response = await this.storeApi.view_store_purchases_history(this.state.store_id);
        alert(response.message);
        if (!response.was_execption) {
            // console.log("in view_store_purchases_history - success!\n");
            // console.log("response.value: ");
            //  console.log(response.value.product_and_name[1]);



            // console.log("response.value.purchaseID_purchases: " + response.value.purchaseID_purchases);
            // console.log(response.value.purchaseID_purchases);

            let res = [];
            // (response.value.purchaseID_purchases).map((pur) => ())
            this.setState({ history: response.value });
            console.log("history: ");
            console.log(this.state.history);
            //  console.log(this.state.history.product_and_name[1])
            //  console.log(this.state.history.product_and_name[0])

            //show history
        }
        else {

        }
    }

    async componentDidMount() {
        this.view_store_purchases_history();
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
                                Store Purchase History
                            </h3>
                        </Grid>

                        <Grid position='center' align='center'>

                            <Demo>
                                <List>
                                    {this.state.history.map((his) => (
                                        <>
                                            <ListItemButton onClick={this.handleClick}
                                                secondaryAction={

                                                    <IconButton edge="end" aria-label="view more">
                                                        <ExpandMoreIcon />
                                                    </IconButton>
                                                }>

                                                {this.state.open ? <ExpandLessIcon /> : <ExpandMoreIcon />}

                                                <ListItemText
                                                    primary={'Purchase date: ' + his.transaction_date}
                                                    secondary={'Total price: ₪ ' + his.purchase.totalPrice}
                                                // primary='fsdf'
                                                />
                                            </ListItemButton>

                                            <Collapse in={this.state.open} timeout="auto" unmountOnExit>
                                                <List component="div" disablePadding>
                                                    <ListItemButton sx={{ pl: 4 }}>
                                                        {/*buyer_email: "amit@gmail.com"
                                                        product_and_name: {1: 'iphone'}
                                                        product_and_quantity: {1: 20}
                                                        product_and_totalPrice: {1: 59998}
                                                        purchase: {transaction_date: '2022-05-24', product_and_quantity: {…}, product_and_totalPrice: {…}, product_and_name: {…}, totalPrice: 59998}
                                                        purchase_id: 0
                                                        totalPrice: 59998
                                                        transaction_date: "2022-05-24"*/}
                                                        <ListItemText secondary={"Purchase ID: " + his.purchase_id
                                                            // + "\n Product: " + his.product_and_name
                                                        } primary={"Buyer email: " + his.buyer_email} />
                                                    </ListItemButton>
                                                </List>
                                            </Collapse>
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
