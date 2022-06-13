import React from 'react';
import { useParams } from 'react-router-dom';
import PaymentPage from './PaymentPage';
import Button from '@mui/material/Button';
import SupplyPage from './SupplyPage';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { CartApi } from '../API/CartApi';

export default function BuyCart() {
    const [payment, setPayment] = React.useState(null);
    const [supply, setSupply] = React.useState(null);
    const update_payment_info = (payment) => {
      console.log(JSON.stringify(payment));
      setPayment(payment);
      setSnackbar({ children: "Payment Information Recieved Successfully", severity: 'success' }); 
      // localStorage.setItem("payment", payment);
    };
    const update_supply_info = (supply) => {
      setSupply(supply);
      console.log(JSON.stringify(supply));
      setSnackbar({ children: "Supply Information Recieved Successfully", severity: 'success' });
        // localStorage.setItem("supply", supply);
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
        if (response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' }); 
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
        <PaymentPage update={update_payment_info.bind(this)}></PaymentPage>
        <SupplyPage update={update_supply_info.bind(this)}></SupplyPage> 
         <Button onClick={() => buy_cart()} variant="contained">Buy Cart </Button>
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
    