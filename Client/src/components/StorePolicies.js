import React, { Component } from 'react';
import { Link } from "react-router-dom";
import HomeIcon from '@mui/icons-material/Home';
import { AdminApi } from '../API/AdminApi';
import { experimentalStyled as styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import FormDialog from './FormDialog';
import FormDialogPermissions from './FormDialogPermissions';
import { StoreApi } from '../API/StoreApi';
import StoreManagmentProductsTable from './StoreManagmentProductsTable';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { Utils } from '../ServiceObjects/Utils';
import List from '@mui/material/List';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemIcon from '@mui/material/ListItemIcon';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import { useParams } from 'react-router-dom';
import { useEffect } from 'react';
import { PoliciesApi } from '../API/PoliciesApi';
import ControlledRadioButtonsGroup from './ControlledRadioButtonsGroup';
import InteractiveList from './InteractiveList';
import FolderIcon from '@mui/icons-material/Folder';
import MoneyOffIcon from '@mui/icons-material/MoneyOff';
import { purple } from '@mui/material/colors';
import AddDiscount from './AddDiscount';
import AddIcon from '@mui/icons-material/Add';
import CreatePredict from './CreatePredict';
import AddPurchase from './AddPurchase';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import QuestionMarkIcon from '@mui/icons-material/QuestionMark';
const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));





export default function StorePolicies() {
    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);
    const {id} = useParams();
    const store_id=id;
    const policiesApi = new PoliciesApi();
    // const save_predict = (perdict) =>{
    //     console.log(perdict);
    //     setPredictChosen(perdict);
    // }

    //Getters
    const [purchases,setPurchases ] =React.useState(["purchase1", "purchase2", "purchase3"]);
    const [predicts,setPredicts ] =React.useState(["predict1", "predict2", "predict3"]);
    const [discounts,setDiscounts ] =React.useState(["discount1", "discount2", "discount3"]);

    //ADD
    const [addDiscount,setAddDiscount ] =React.useState(false);
    const [addPurchase,setAddPurchase ] =React.useState(false);
    const [addPredict,setAddPredict ] =React.useState(false);




    const get_purchase_policy = async () => {
        const response = policiesApi.get_purchase_policy(id);
        if(!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });     }
        else{
            setSnackbar({ children: response.message, severity: 'error' }); 
        }
    }
    const send_predicts = async () => {
        const response = policiesApi.send_predicts(id);
        if(!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });     }
        else{
            setSnackbar({ children: response.message, severity: 'error' }); 
        }
    }
    const get_discount_policy = async () => {
        const response = policiesApi.get_discount_policy(id);
        if(!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });     }
        else{
            setSnackbar({ children: response.message, severity: 'error' }); 
        }
    }
    useEffect(()=>{get_purchase_policy()}, []);
    useEffect(()=>{send_predicts()}, []);
    useEffect(()=>{get_discount_policy()}, []);

    const ShowAddPredict = ()=>{
        setAddPredict(!addPredict);
    }
    const ShowAddDiscount = ()=>{
        setAddDiscount(!addDiscount);
    }
    const ShowAddPurchase = ()=>{
        setAddPurchase(!addPurchase);
    }

    

    //Functions to Server
    const remove_predict = async (predict_name) =>{
        console.log(predict_name);
        const response = await policiesApi.remove_predict(store_id, predict_name)
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });

        }
        else{
            setSnackbar({ children: response.message, severity: 'error' });

        }
    }
    const remove_discount_rule = async (discount_rule) =>{
        console.log(discount_rule);
        const response = await policiesApi.remove_discount_rule(store_id, discount_rule)
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });

        }
        else{
            setSnackbar({ children: response.message, severity: 'error' });

        }
    }
    const remove_purchase_rule = async (purchase_rule) =>{
        console.log(purchase_rule);
        const response = await policiesApi.remove_purchase_rule(store_id, purchase_rule)
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });

        }
        else{
            setSnackbar({ children: response.message, severity: 'error' });

        }
    }
        return (

            <Box sx={{ flexGrow: 1 }}>
                
                <h3 align="center">Store Policies</h3>
                <Grid>
                </Grid>
                <Grid container spacing={5} paddingRight={25} paddingLeft={25} paddingTop={10}>
                    <Grid item xs={10}>  <Item> 
                        <InteractiveList name="predicts" list={predicts} icon={<QuestionMarkIcon></QuestionMarkIcon>} remove={remove_predict}></InteractiveList>
                        <Button onClick={ShowAddPredict}><AddIcon></AddIcon></Button> 
                        {addPredict ? <CreatePredict></CreatePredict>:null}</Item>                    
                    </Grid>

                    <Grid item xs={10}>  <Item> 
                    <InteractiveList name="Discount Rules" list={discounts} icon={<MoneyOffIcon></MoneyOffIcon>} remove={remove_discount_rule}></InteractiveList>
                        <Button onClick={ShowAddDiscount}><AddIcon></AddIcon></Button> 
                        {addDiscount ? <AddDiscount></AddDiscount>:null}</Item>                    
                    </Grid>

                    <Grid item xs={10}>  <Item> 
                    <InteractiveList name="Purchase Rules" list={purchases} icon={<ShoppingCartIcon></ShoppingCartIcon>} remove={remove_purchase_rule}></InteractiveList>
                        <Button onClick={ShowAddPurchase}><AddIcon></AddIcon></Button> 
                        {addPurchase ? <AddPurchase></AddPurchase>:null}</Item>                    
                    </Grid>



                    <Grid item xs={10}>  <Item> </Item>                    </Grid>
                    
                    
                    {/* <ControlledRadioButtonsGroup list={predicts} name="Predicts" save={save_predictOr1}></ControlledRadioButtonsGroup> */}





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
                <h3> </h3>
            </Box>


        );

    

}