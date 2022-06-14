import * as React from 'react';
import { Component } from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import Grid from '@mui/material/Grid';
import Link from '@mui/material/Button';
import PersonIcon from '@mui/icons-material/Person';
import { StoreApi } from '../API/StoreApi';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import FormDialog from './FormDialog';
import { Utils } from '../ServiceObjects/Utils';

const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));



export default class ViewBidsStatus extends Component {

    constructor(props) {
        super(props);
        this.state = {
            store_id:this.props.store_id,
            bids: [],
            manager_answer_bid_fields:["Bid ID", "'Yes' for confirm , 'No' for reject", "Negotiation Price"],
            snackbar: null,
        };
        this.storeApi = new StoreApi();
        console.log("ViewBidsStatus, id = "+this.props.store_id);



    }

    async view_bids_status() {
        let response = await this.storeApi.view_bids_status(this.state.store_id);
        console.log(response.value);
        response.value.map(a=>console.log(a));
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
            console.log("in get store staff info - success!\n");
            this.setState({ bids: response.value });
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });


        }
    }
    async manager_answer_bid(values) {
        const bid_id = values[0]; 
        const manager_answer = values[1];
        if (Utils.check_yes_no() == 0)
        {
            this.setState({ snackbar: { children: "Answer must only be 'Yes' or 'No' ", severity: "error" } });
            return;
        } 
        const price = values[2]; 
        console.log(bid_id);
        console.log(manager_answer);
        console.log(price);
        let response = await this.storeApi.manager_answer_bid(this.state.store_id, bid_id, manager_answer, price);
        if (!response.was_exception) {
            this.setState({ snackbar: { children: response.message, severity: "success" } });
        }
        else {
            this.setState({ snackbar: { children: response.message, severity: "error" } });


        }
    }

    async componentDidMount() {
        this.view_bids_status();
    }

    render() {
        return (

            <>

                <Box position='center' align='center'>
                    <Grid position='center' row-spacing={3}>
                        <Grid item>
                            <h3 class="Header" align="center">
                                Bids Status
                            </h3>
                        </Grid>

                        <Grid position='center' align='center'>

                            <Demo>
                                <List>
                                    {

                                        this.state.bids.map((bid) => (
                                            <ListItem>
                                                <ListItemAvatar>
                                                    <Avatar>
                                                    bid.id
                                                    </Avatar>
                                                </ListItemAvatar>
                                                <ListItemText
                                                    
                                                    primary={"Offered "  + bid.price +"$"+" for "+bid.quantity+" "+bid.product_info.name+" Products"  }
                                                    secondary={"bid status: "+bid.status}

                                                />
                                            </ListItem>
                                            

                                        ))




                                    }
                                    <FormDialog fields={this.state.manager_answer_bid_fields} getValues={this.manager_answer_bid.bind(this)}  name="Answer Bid"></FormDialog>
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
        // )
        // );

    }
}