import React from 'react';
import PaymentPage from './PaymentPage';
import Button from '@mui/material/Button';
import SupplyPage from './SupplyPage';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { CartApi } from '../API/CartApi';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LinearDeterminate from './LinearDeterminate';
import { Utils } from '../ServiceObjects/Utils';

export default function BuyCart() {
    const [loading, setLoading] = React.useState(false);


    const [payment, setPayment] = React.useState(null);
    const [supply, setSupply] = React.useState(null);
    const update_payment_info = (payment) => {
      console.log(JSON.stringify(payment));
      setPayment(payment);
      setSnackbar({ children: "Payment Information Recieved Successfully", severity: 'success' }); 
    };
    const update_supply_info = (supply) => {
      setSupply(supply);
      console.log(JSON.stringify(supply));
      setSnackbar({ children: "Supply Information Recieved Successfully", severity: 'success' });
    };
    const cartApi = new CartApi();
    const buy_cart = async () => {
        if (payment === null) 
        {
            setSnackbar({ children: "Please complete Payment Information", severity: 'error' });   
            return;
        }
        if (supply === null) 
        {
            setSnackbar({ children: "Please complete Supply Information", severity: 'error' }); 
            return;
        }
        const payment_info = JSON.stringify(payment);
        const supply_info = JSON.stringify(supply);
        setLoading(true);
        let response = await cartApi.buy_cart(payment_info,supply_info);
        if (!response.was_exception)
        {
            setLoading(false);
            await Utils.sleep(2000)
            setSnackbar({ children: response.message, severity: 'success' });
            window.location.href=`/`
        }
        else
        {
          setLoading(false);
          setSnackbar({ children: response.message, severity: 'error' }); 
    
        }
            
        };
    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);
    
    return (
        !loading ?
        <main>
          <Box sx={{ flexGrow: 1 }}>

            <h1 class="Header" align="center">
                            </h1>
            <h2 class="Header" align="center">
            Buy Cart
                            </h2>
                            <h5 class="Header" align="center"> 
                Please complete your supply and payment information
                            </h5>
            </Box>

          <Grid container spacing={10} justifyContent="center" alignItems="center">
          
                        
          <PaymentPage update={update_payment_info}></PaymentPage>  
          <SupplyPage update={update_supply_info}></SupplyPage>                 
          
          </Grid>
          <Grid container spacing={3} justifyContent="center" alignItems="center" paddingBottom={20}>

          <Button onClick={buy_cart} variant="contained" >Buy Cart </Button>   
        


          </Grid>
        
        
         
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
        </main> : <Grid container spacing={6} paddingRight={25} paddingLeft={25} paddingTop={10}> 
        <Grid container spacing={6} paddingRight={25} paddingLeft={50} paddingTop={10}> 

          <h1> Connecting to external services</h1>
        </Grid>
        <LinearDeterminate></LinearDeterminate>
        </Grid>
      ); 
}
    