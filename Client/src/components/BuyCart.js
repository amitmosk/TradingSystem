import React from 'react';
import { useParams } from 'react-router-dom';
import PaymentPage from './PaymentPage';
import Button from '@mui/material/Button';
import SupplyPage from './SupplyPage';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { CartApi } from '../API/CartApi';

export default function BuyCart() {
    const update_payment_info = (payment) => {
    localStorage.setItem("payment", payment);
    };
    const update_supply_info = (supply) => {
        localStorage.setItem("supply", supply);
    };
    const cartApi = new CartApi();
    const buy_cart = async () => {
        if (localStorage.getItem('payment') === null) 
        {
            this.setSnackbar({ children: "Please complete Payment Information", severity: "error" });
            return;
        }
        if (localStorage.getItem('supply') === null) 
        {
            this.setSnackbar({ children: "Please complete Supply Information", severity: "error" });
            return;
        }
        const payment_info = localStorage.getItem('payment');
        const supply_info = localStorage.getItem('supply');
        console.log(payment_info);
        console.log(supply_info);
        let response = await cartApi.buy_cart(payment_info,supply_info);
        if (response.was_exception)
        {
            this.setSnackbar({ children: response.message, severity: "success" });
        }  
        else
        {
            this.setSnackbar({ children: response.message, severity: "error" });
    
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
    