import React from 'react';
import PaymentPage from './PaymentPage';
import Button from '@mui/material/Button';
import SupplyPage from './SupplyPage';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { CartApi } from '../API/CartApi';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';

export default function BuyCart() {
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
        let response = await cartApi.buy_cart(payment_info,supply_info);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
            window.location.href=`/`
        }  
        else
        {
          setSnackbar({ children: response.message, severity: 'error' }); 
    
        }
            
        };
    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);
    return (
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
        </main>
      );
}
    