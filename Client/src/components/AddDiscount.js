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


export default function AddDiscount() {
    const ADD = "Add ";
    const DISCOUNT_RULE = " Discount Rule";
    
    // localStorage.setItem("option", "Simple");
    const [option, setOption] = React.useState("Simple");
    useEffect(()=>{add_complex_discount_rule()}, option);
    const add_simple_discount_rule_fields =["aaaa"]
    const handleCloseSnackbar = () => setSnackbar(null);
    const handleInputChange = event => {
        const name = event.target.name
        const value = event.target.value;
        console.log(name);
        console.log(value);
        setOption(value);
        // localStorage.setItem(name, value);
      };
    const [snackbar, setSnackbar] = React.useState(null);
    const hadleSubmit = async () => {}
    const add_discount_rule = () =>  {
        console.log("in add discount rule");
        switch (option) {
            case "Simple":
                console.log("option");
                
                // return AddDiscount.render(<FormDialog fields={state.add_simple_discount_rule_fields} getValues={add_simple_discount_rule.bind(this)} name="Close Store"></FormDialog>);
                // this.add_simple_discount_rule()
                break;
            
            case "Complex Or":
                add_composite_or_discount_rule()
                break;
            case "Complex And":
                add_composite_and_discount_rule()
                break;
            case "Complex Xor":
                add_composite_xor_discount_rule()
                break;
            case "Complex Max":
                add_complex_discount_rule()
                break;
            case "Complex Plus":
                add_complex_discount_rule()
                break;
            default:
                console.log("option is empty");
        }

    }
    const  add_simple_discount_rule = async () => {
    }
    const add_complex_discount_rule = async() => {
        console.log("yessssssssssssssssssssssssssssssss");
    }
    const add_composite_or_discount_rule = async () => {
    }
    const add_composite_and_discount_rule = async () => {
    }
    const add_composite_xor_discount_rule = async () =>{
    }

    return (
        <>

            <Box sx={{ flexGrow: 1 }}>

            <h3>Add Discount Rule</h3>

            <Grid container spacing={20}>
                
           
                    {/* <Input name="val" placeholder={ADD + option+ DISCOUNT_RULE} onChange={handleInputChange} required/> */}
                    <Button onClick={() => add_discount_rule} variant="contained">Add </Button>
                    
                    {/* <FormDialog fields={this.state.close_store_fields} getValues={this.close_store_permanently.bind(this)} name="Close Store"></FormDialog> */}
                    


            </Grid>
           

            <Box sx={{ flexGrow: 3 }}>

            </Box>
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
                    {/* {option == "Simple" ? <FormDialog fields={add_simple_discount_rule_fields} getValues={add_discount_rule} name="Close Store"></FormDialog> : null} */}
                    {option == "Simple" ? <Button>kkkkkkkkkkk</Button> : null}
                    {/* {option == 1 ? <FormDialog fields={add_simple_discount_rule_fields} getValues={add_discount_rule} name="Close Store"></FormDialog> : null}
                    {option == 1 ? <FormDialog fields={add_simple_discount_rule_fields} getValues={add_discount_rule} name="Close Store"></FormDialog> : null}
                    {option == 1 ? <FormDialog fields={add_simple_discount_rule_fields} getValues={add_discount_rule} name="Close Store"></FormDialog> : null}
                    {option == 1 ? <FormDialog fields={add_simple_discount_rule_fields} getValues={add_discount_rule} name="Close Store"></FormDialog> : null} */}
                </Grid>
                <Grid item>
                    <Grid container direction="column" alignItems="center">
                        
                        
                    </Grid>
                </Grid>
                <Grid item>
                    hhhhhhhhhhhhhhhhhhhhhh
                </Grid>
                <Grid item>
                    hhhhhhhhhhhhhhhhhhhhhh
                </Grid>
                <Grid item><Button variant="contained" onClick={add_discount_rule}>Submit</Button></Grid>
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
























































