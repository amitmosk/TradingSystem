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
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import FormDialog from './FormDialog';
import { Utils } from '../ServiceObjects/Utils';
import { useParams } from 'react-router-dom';
import { useEffect } from 'react';
import { StoreApi } from '../API/StoreApi';
import { SettingsAccessibility } from '@mui/icons-material';
import { isDate } from 'date-fns';
import ControlledRadioButtonsGroup from './ControlledRadioButtonsGroup';
import Button from "@mui/material/Button";



const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
}));


export default function ViewBidsStatus() {

    let bid_id=null;
    const bid_ids = [];
    const {id} = useParams();
    const store_id = id;
    const[negoprice, setNegoprice] = React.useState(-1);
    const[bids, setBids] = React.useState([]);
    const[bidids, setBidids] = React.useState([]);
    const[bidid, setBidid] = React.useState(null);
    const[approve, setApprove] = React.useState(false);
    const[isanswer,setIsanswer ] = React.useState(false);
    const[nego, setNego] = React.useState(false);
    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);
    //setSnackbar({ children: response.message, severity: 'success' });
    const is_confirm_fields = ["Bid ID"];
    const manager_answer_bid_fields=["Negotiation Price"];
    const storeApi = new StoreApi();
    useEffect(()=>{view_bids_status()}, []);  



    

    const view_bids_status = async()=> {
        const storeApi = new StoreApi();
        let response = await storeApi.view_bids_status(store_id);
        console.log(response);
        console.log(response.value);
        response.value.map(a=>console.log(a));
        if (!response.was_exception) {
            setSnackbar({ children: response.message, severity: 'success' });
            const bids = response.value;
            setBids(bids);
            bids.map((bid)=>!bid_ids.includes(bid.id) ?  bid_ids.push(bid.id):null);
            setBidids(bid_ids);
            console.log(bidids);

        }
        else {
            setSnackbar({ children: response.message, severity: 'error' });


        }
        console.log(bids);
    }
    const save_bid_id=(bid_id)=>
    {
        setBidid(bid_id);
    }
    const get_status = (enum_status) =>
    {
        // open_waiting_for_answers,
        // closed_denied,
        // closed_confirm,
        // negotiation_mode,
        if(enum_status === "open_waiting_for_answers")
            return "Open Waiting For Answers"; 
        if(enum_status === "closed_denied")
            return "Closed Denied"; 
        if(enum_status === "closed_confirm")
            return "Closed Confirm"; 
        if(enum_status === "negotiation_mode")
            return "Negotiation Mode"; 
            

    }
    const is_answerd = ()=>
    {
        setIsanswer(!isanswer);
    }
    const is_confirm = async (values) => {
        console.log(values);
        values[0] == 0 ? setApprove(false) : setApprove(true);
        //Manager denied the bid
        if(!values[0])
        {
            console.log("Manager Answer Bid HTTP");
            console.log(`store_id is ${store_id}`);
            console.log(`bidid is ${bidid}`);
            console.log(`approve is ${approve}`);
            console.log(`nego_price is ${negoprice}`);
            const response = await storeApi.manager_answer_bid(store_id, bidid, approve, negoprice);
            if (!response.was_exception) {
                
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();
                //TODO - Tom - remove the reload from comment
                // window.location.reload();
            }
            else {
                setSnackbar({ children: response.message, severity: 'error' });
            }
            
        }
        
    }
    const is_add_nego =async (values) =>{
        console.log(values);
        setNego(values[0]);
        if(!values[0])
        {
            console.log("Manager Answer Bid HTTP");
            console.log(`store_id is ${store_id}`);
            console.log(`bidid is ${bidid}`);
            console.log(`approve is ${approve}`);
            console.log(`nego_price is ${negoprice}`);
            const response = await storeApi.manager_answer_bid(store_id, bidid, approve, negoprice);
            if (!response.was_exception) {
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();
            }
            else {
                setSnackbar({ children: response.message, severity: 'error' });
            }
        }
    }
    const manager_answer_bid = async (values) => {
        setNegoprice(values[0]);
        if(Utils.check_all_digits(values[0]) == 0 || Utils.check_not_empty(values[0])==0)
        {
            setSnackbar({ children: "Illegal negotiation price", severity: 'error' });
            return ;
        }
        const storeApi = new StoreApi();
        console.log("Manager Answer Bid HTTP");
        console.log(`store_id is ${store_id}`);
        console.log(`bidid is ${bidid}`);
        console.log(`approve is ${approve}`);
        console.log(`nego_price is ${negoprice}`);
        const response = await storeApi.manager_answer_bid(store_id, bidid, approve, negoprice);
        if (!response.was_exception) {
            setSnackbar({ children: response.message, severity: 'success' });
            window.location.reload();
        }
        else {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }


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
                                    {bids.length!==0?

                                        bids.map((bid) => (
                                            <ListItem>
                                                <ListItemAvatar>
                                                    <Avatar>
                                                   {bid.id}
                                                    </Avatar>
                                                </ListItemAvatar>
                                                <ListItemText
                                                    
                                                    primary={`Offered ${bid.price}$ for ${bid.quantity} ${bid.product_info.name} Products By ${bid.buyer_mail}`}
                                                    secondary={"bid status: "+get_status(bid.status)}

                                                />
                                            </ListItem>
                                            

                                        )) : <h3 style={{ color: 'red' }}>No Bids To This Store</h3>




                                    }
                                    
                                    
                                    
                                    
                                </List>
                            </Demo>

                            <Grid container spacing={3} justifyContent="center" alignItems="center" paddingTop={8}> 
                            
                            
                            {bids.length!==0? <Button onClick={is_answerd}>Answer Bid</Button> : null}
                            </Grid>
                            <Grid container spacing={3} justifyContent="center" alignItems="center" paddingTop={8}> 
                            {isanswer ? <ControlledRadioButtonsGroup list={bidids} name="Choose Bid ID to Answer" save={save_bid_id}></ControlledRadioButtonsGroup> : null}
                            </Grid>
                            <Grid container spacing={3} justifyContent="center" alignItems="center" paddingTop={8}> 
                            {bidid!==null && !approve ? <FormDialog fields={[]} getValues={is_confirm}  name={`Answer Bid ${bidid}`} title="Approve this Bid?" submit_button="Yes" cancel_button={"No"} to_open={true}></FormDialog>:null}
                                    {approve && !nego ? <FormDialog fields={[]} getValues={is_add_nego}  name="Add Negotiation Price" title="Add A Negotioation Price?" submit_button="Yes" cancel_button={"No"} to_open={true}></FormDialog>:null}
                                    {nego ? <FormDialog fields={manager_answer_bid_fields} getValues={manager_answer_bid}  name="Add Negotiation Price" title="Add A Negotioation Price?" submit_button="Submit"  to_open={true}></FormDialog>:null}


                      
                            
                           
                            
                            </Grid>
                        </Grid>
                    </Grid>
                </Box>
                {!!snackbar && (
            <Snackbar
            open
            anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
            onClose={handleCloseSnackbar}
            autoHideDuration={6000}
            >
            <Alert {...snackbar} onClose={handleCloseSnackbar} />
            </Snackbar>
        )}

            </>
        );
        // )
        // );

    
    }
