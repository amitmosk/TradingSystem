import List from '@mui/material/List';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemIcon from '@mui/material/ListItemIcon';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { StoreApi } from '../API/StoreApi';
import { useParams } from 'react-router-dom';
import { Utils } from '../ServiceObjects/Utils';
import { useEffect } from 'react';
import React, { Component } from 'react';
import Link from '@mui/material/Button';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import HomeIcon from '@mui/icons-material/Home';
import { Input } from "@mui/material";
import FormDialog from './FormDialog';
import { Category } from '@mui/icons-material';

export default function AddDiscount() {
    const ADD = "Add ";
    const DISCOUNT_RULE = " Discount Rule";
    const [checked, setChecked] = React.useState(true);

    const handleChange = (event) => {
        setChecked(event.target.checked);
    };
    // localStorage.setItem("option", "Simple");
    const [option, setOption] = React.useState("Simple");
    const [simpleOptions, setSimpleOptions] = React.useState("Store");
    // useEffect(()=>{add_complex_discount_rule()}, option);
    const predict_list =[1, 2, 3,4 ,5  ];
    const rules_list =[1, 2, 3,4 ,5  ];
    const add_simple_discount_rule_for_store_fields =["Rule Name", "Discount Precent"];
    const add_simple_discount_rule_for_category_fields =["Rule Name", "Discount Precent", "Category Name"];
    const add_simple_discount_rule_for_product_fields =["Rule Name", "Discount Precent", "Product ID"];
    const add_complex_and_discount_rule_fields=["Rule Name"];
    const add_complex_or_discount_rule_fields=["a"];
    const add_complex_xor_discount_rule_fields=["a"];
    const add_complex_max_discount_rule_fields=["a"];
    const add_complex_plus_discount_rule_fields=["a"];
    const handleCloseSnackbar = () => setSnackbar(null);
    const handleInputChange = event => {
        const name = event.target.name
        const value = event.target.value;
        console.log(name);
        console.log(value);
        setOption(value.toString());
        // localStorage.setItem(name, value);
      };
    const handleInputChange_simple = event => {
    const name = event.target.name
    const value = event.target.value;
    console.log(name);
    console.log(value);
    setSimpleOptions(value.toString());
    // localStorage.setItem(name, value);
    };
    const [snackbar, setSnackbar] = React.useState(null);
    const hadleSubmit = async () => {}

    const  add_simple_discount_rule_for_store = async () => {
    }
    const  add_simple_discount_rule_for_category = async () => {
    }
    const  add_simple_discount_rule_for_product = async () => {
    }
    const add_complex_and_discount_rule = async() => {
        console.log("yessssssssssssssssssssssssssssssss");
    }
    const add_complex_or_discount_rule = async() => {
        console.log("yessssssssssssssssssssssssssssssss");
    }
    const add_complex_xor_discount_rule = async() => {
        console.log("yessssssssssssssssssssssssssssssss");
    }
    const add_complex_max_discount_rule = async() => {
        console.log("yessssssssssssssssssssssssssssssss");
    }
    const add_complex_plus_discount_rule = async() => {
        console.log("yessssssssssssssssssssssssssssssss");
    }
    
    return (
        <>

            <Box sx={{ flexGrow: 1 }}>

            <h3>Add Discount Rule</h3>


            <h3> </h3>
            </Box>
            
            <Grid container spacing={3} justifyContent="center" alignItems="center">

                <Grid item>
                <select name="option"  onChange={handleInputChange} required>
                        <option value="Simple">Simple</option>
                        <option value="Complex And">Complex And</option>
                        <option value="Complex Or">Complex Or</option>
                        <option value="Complex Xor">Complex Xor</option>
                        <option value="Complex Max">Complex Max</option>
                        <option value="Complex Plus">Complex Plus</option>


                    </select>
                    {option == "Simple" ? <select name="simpleOptions"  onChange={handleInputChange_simple} required>
                        <option value="Store">Store</option>
                        <option value="Category">Category</option>
                        <option value="Product">Product</option>


                    </select>
                    : null}
                    
                </Grid>
                <Grid item>
                    <Grid container direction="column" alignItems="center">
                        
                        
                    </Grid>
                </Grid>
                <Grid item>
                {option == "Simple" && simpleOptions === "Store" ? <FormDialog fields={add_simple_discount_rule_for_store_fields} getValues={add_simple_discount_rule_for_store} name="Add Simple Discount Rule For Store"></FormDialog>: null}
                {option == "Simple" && simpleOptions === "Category" ? <FormDialog fields={add_simple_discount_rule_for_category_fields} getValues={add_simple_discount_rule_for_category} name="Add Simple Discount Rule For Category"></FormDialog>: null}
                {option == "Simple" && simpleOptions === "Product" ? <FormDialog fields={add_simple_discount_rule_for_product_fields} getValues={add_simple_discount_rule_for_product} name="Add Simple Discount Rule For Product"></FormDialog>: null}
                {option == "Complex And" ? <FormDialog fields={add_complex_and_discount_rule_fields} getValues={add_complex_and_discount_rule} name="Add Complex And Discount Rule"></FormDialog> : null}
                    {option == "Complex Or" ? <FormDialog fields={add_complex_or_discount_rule_fields} getValues={add_complex_or_discount_rule} name="Add Complex Or Discount Rule"></FormDialog> : null}
                    {option == "Complex Xor" ? <FormDialog fields={add_complex_xor_discount_rule_fields} getValues={add_complex_xor_discount_rule} name="Add Complex Xor Discount Rule"></FormDialog> : null}
                    {option == "Complex Max" ? <FormDialog fields={add_complex_max_discount_rule_fields} getValues={add_complex_max_discount_rule} name="Add Complex Max Discount Rule"></FormDialog> : null}
                    {option == "Complex Plus" ? <FormDialog fields={add_complex_plus_discount_rule_fields} getValues={add_complex_plus_discount_rule} name="Add Complex Plus Discount Rule"></FormDialog> : null}
                </Grid>
                <Grid item>
                {option == "Complex And" ? <select name="And"  required>
                    {predict_list.map((p)=><option value={p.toString()}>  {p}</option>)} 
                         </select> : null}
                {option == "Complex Or" ? <select name="Or"  required>
                    {predict_list.map((p)=><option value={p.toString()}>  {p}</option>)} 
                         </select> : null}
                {option == "Complex Xor" ? <select name="Xor"  required>
                    {rules_list.map((p)=><option value={p.toString()}>  {p}</option>)} 
                         </select> : null}
                {option == "Complex Max" ? <select name="Max"  required>
                    {rules_list.map((p)=><option value={p.toString()}>  {p}</option>)} 
                         </select> : null}
                {option == "Complex Plus" ? <select name="Plus"  required>
                    {rules_list.map((p)=><option value={p.toString()}>  {p}</option>)} 
                         </select> : null}
                        
                        
                    {/* {option == "Complex Or" ? <FormDialog fields={add_complex_or_discount_rule_fields} getValues={add_complex_or_discount_rule} name="Add Complex Or Discount Rule"></FormDialog> : null}
                    {option == "Complex Xor" ? <FormDialog fields={add_complex_xor_discount_rule_fields} getValues={add_complex_xor_discount_rule} name="Add Complex Xor Discount Rule"></FormDialog> : null}
                    {option == "Complex Max" ? <FormDialog fields={add_complex_max_discount_rule_fields} getValues={add_complex_max_discount_rule} name="Add Complex Max Discount Rule"></FormDialog> : null}
                    {option == "Complex Plus" ? <FormDialog fields={add_complex_plus_discount_rule_fields} getValues={add_complex_plus_discount_rule} name="Add Complex Plus Discount Rule"></FormDialog> : null} */}
                </Grid>
                <Grid item>

                </Grid>
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
        </>
    );
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
























































