import List from '@mui/material/List';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemIcon from '@mui/material/ListItemIcon';
// import Checkbox from '@mui/material/Checkbox';
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
import { PoliciesApi } from '../API/PoliciesApi';
import CheckboxesGroup from './CheckboxesGroup';
import ControlledRadioButtonsGroup from './ControlledRadioButtonsGroup';
import StoreProductsTable from './StoreProductsTable';
import Checkbox from './Checkbox';
import { Row, Col } from 'react-grid-system';

export default function AddDiscount() {
    const {id} = useParams();
    const store_id =id;

    //fields
    const add_simple_discount_rule_for_store_fields =["Rule Name", "Discount Precent(%)"];
    const add_simple_discount_rule_for_category_fields =["Rule Name", "Discount Precent(%)"];
    const add_simple_discount_rule_for_product_fields =["Rule Name", "Discount Precent(%)", "Product ID"];
    const add_complex_discount_rule_fields=["Rule Name"];
    const add_complex_and_discount_rule_fields=["Rule Name"];
    const add_complex_or_discount_rule_fields=["Rule Name"];
    const add_complex_xor_discount_rule_fields=["Rule Name"];
    const add_complex_max_discount_rule_fields=["Rule Name"];
    const add_complex_plus_discount_rule_fields=["Rule Name"];

    //API 
    const policiesApi = new PoliciesApi();
    const storeApi = new StoreApi();

    // list of categories, Predicts, discounts

    const [categories,setCategories ] =React.useState(["car1", "cat2", "cat3"]);
    const [predicts,setPredicts ] =React.useState(["predicts1", "predicts2", "Predicts3"]);
    const [discounts,setDiscounts ] =React.useState(["discount1", "discount2", "discount3"]);
    const get_categories_of_stores = async () =>
    {
        const response = await storeApi.get_all_categories(store_id);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
            setCategories(response.value);
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });

        }
    }
    const get_discount_predict_lists = async () =>
    {
        const response = await policiesApi.send_predicts(store_id);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
            setPredicts(response.value);
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });

        }
        response = await policiesApi.get_discount_policy(store_id);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
            setDiscounts(response.value);
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });

        }
    }
    useEffect(()=>{get_discount_predict_lists()}, []);  
    useEffect(()=>{get_categories_of_stores()}, []);


    const [predictChosen, setPredictChosen] = React.useState(null);
    const [discountChosen, setDiscounChosen] = React.useState(null);
    const [categoryChosen, setCategoryChosen] = React.useState(null);
    const save_predict = (perdict) =>{
        console.log(perdict);
        setPredictChosen(perdict);
    }  
    const save_discount_rule = (discount_rule) =>{
        console.log(discount_rule);
        setDiscounChosen(discount_rule);
    }  
    const save_category = (category) =>{
        console.log(category);
        setCategoryChosen(category);
    }

    const [checked, setChecked] = React.useState(true);
    const [ruleName, setRuleName] = React.useState(undefined);
    
    const handleChange = (event) => {setChecked(event.target.checked)};
    
    
    
    
    


    //------------- complex and----------------------
    const [predictChosenAnd1, setPredictChosenAnd1] = React.useState(null);
    const [predictChosenAnd2, setPredictChosenAnd2] = React.useState(null);

    const save_predictAnd1 = (perdict) =>{
        console.log(perdict);
        setPredictChosenAnd1(perdict);
    }
    const save_predictAnd2 = (discount_rule) =>{
        console.log(discount_rule);
        setPredictChosenAnd2(discount_rule);
    }
    //------------- complex or----------------------

    const [predictChosenOr1, setPredictChosenOr1] = React.useState(null);
    const [predictChosenOr2, setPredictChosenOr2] = React.useState(null);
    const save_predictOr1 = (perdict) =>{
        console.log(perdict);
        setPredictChosenOr1(perdict);
    }
    const save_predictOr2 = (perdict) =>{
        console.log(perdict);
        setPredictChosenOr2(perdict);
    }

    //------------- discount xor----------------------

    const [discountChosenXor1, setDiscountChosenXor1] = React.useState(null);
    const [discountChosenXor2, setDiscountChosenXor2] = React.useState(null);
    const save_discountXor1 = (discount) =>{
        console.log(discount);
        setDiscountChosenXor1(discount);
    }
    const save_discountXor2 = (discount) =>{
        console.log(discount);
        setDiscountChosenXor2(discount);
    }

    //------------- discount max----------------------

    const [discountChosenMax1, setDiscountChosenMax1] = React.useState(null);
    const [discountChosenMax2, setDiscountChosenMax2] = React.useState(null);
    const save_discountMax1 = (discount) =>{
        console.log(discount);
        setDiscountChosenMax1(discount);
    }
    const save_discountMax2 = (discount) =>{
        console.log(discount);
        setDiscountChosenMax2(discount);
    }


    //------------- discount plus----------------------

    const [discountChosenPlus1, setDiscountChosenPlus1] = React.useState(null);
    const [discountChosenPlus2, setDiscountChosenPlus2] = React.useState(null);
    const save_discountPlus1 = (discount) =>{
        console.log(discount);
        setDiscountChosenPlus1(discount);
    }
    const save_discountPlus2 = (discount) =>{
        console.log(discount);
        setDiscountChosenPlus2(discount);
    }
    
    
    
    
    
    const [option, setOption] = React.useState("Simple");
    const [simpleOptions, setSimpleOptions] = React.useState("Store");
    
    
    const handleCloseSnackbar = () => setSnackbar(null);
    const handleInputChange = event => {
        const name = event.target.name
        const value = event.target.value;
        console.log(name);
        console.log(value);
        setOption(value.toString()); console.log(simpleOptions) };
    const handleInputChange_simple = event => {
        const name = event.target.name
        const value = event.target.value;
        console.log(name);
        console.log(value);
        setSimpleOptions(value);
    };
    const [snackbar, setSnackbar] = React.useState(null);
    const hadleSubmit = async () => {}


    //Functions To Server
    const add_simple_discount_rule_for_store = async (values) => {
        const rule_name = values[0];
        const discount_precent = values[1];
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        if (Utils.check_precent(discount_precent) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_simple_store_discount_rule(store_id, discount_precent, rule_name); 
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }

    }
    const  add_simple_discount_rule_for_category = async (values) => {
        const rule_name = values[0];
        const discount_precent = values[1];
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        if (Utils.check_precent(discount_precent) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_simple_categorey_discount_rule(store_id, categoryChosen, discount_precent, rule_name); 
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    const  add_simple_discount_rule_for_product = async (values) => {
        const rule_name = values[0];
        const discount_precent = values[1];
        const product_id = values[2];
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        if (Utils.check_precent(discount_precent) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        //TODO: check if product id is legal
        const response = await policiesApi.add_simple_product_discount_rule(store_id, product_id, categoryChosen, discount_precent, rule_name); 
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    const add_complex_discount_rule = async(values) => {
        console.log(values[0]);
        setRuleName(values[0]);
        if (predictChosen == null)
        {
            setSnackbar({ children: "Have to choose Predict", severity: 'error' });
            return;
        }
        if (discountChosen == null)
        {
            setSnackbar({ children: "Have to choose Discount Rule", severity: 'error' });
            return;
        }
        if (ruleName == null)
        {
            setSnackbar({ children: "Have to choose Name For Rule", severity: 'error' });
            return;
        }
        if (Utils.check_rule_name(ruleName) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_complex_discount_rule(store_id, predictChosen, discountChosen, ruleName);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
        
    }
    const add_complex_and_discount_rule = async(values) => {
        const rule_name = values[0];
        console.log(values[0]);
        setRuleName(values[0]);
        if (predictChosenAnd1 == null || predictChosenAnd1==null)
        {
            setSnackbar({ children: "Have to choose Predict", severity: 'error' });
            return;
        }
        if (ruleName == null)
        {
            setSnackbar({ children: "Have to choose Name For Rule", severity: 'error' });
            return;
        }
        if (Utils.check_rule_name(ruleName) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_and_discount_rule(predictChosenAnd1, predictChosenAnd2, store_id, ruleName);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    const add_complex_or_discount_rule = async(values) => {
        const rule_name = values[0];
        console.log(values[0]);
        setRuleName(values[0]);
        if (predictChosenOr1 == null || predictChosenOr2==null)
        {
            setSnackbar({ children: "Have to choose Predict", severity: 'error' });
            return;
        }
        if (ruleName == null)
        {
            setSnackbar({ children: "Have to choose Name For Rule", severity: 'error' });
            return;
        }
        if (Utils.check_rule_name(ruleName) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_or_discount_rule(predictChosenOr1, predictChosenAnd2, store_id, ruleName);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    const add_complex_xor_discount_rule = async(values) => {
        const rule_name = values[0];
        console.log(values[0]);
        setRuleName(values[0]);
        if (predictChosenOr1 == null || predictChosenOr2==null)
        {
            setSnackbar({ children: "Have to choose Predict", severity: 'error' });
            return;
        }
        if (ruleName == null)
        {
            setSnackbar({ children: "Have to choose Name For Rule", severity: 'error' });
            return;
        }
        if (Utils.check_rule_name(ruleName) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_xor_discount_rule(discountChosenXor1, discountChosenXor2, store_id, ruleName);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    const add_complex_max_discount_rule = async(values) => {
        const rule_name = values[0];
        console.log(values[0]);
        setRuleName(values[0]);
        if (predictChosenOr1 == null || predictChosenOr2==null)
        {
            setSnackbar({ children: "Have to choose Predict", severity: 'error' });
            return;
        }
        if (ruleName == null)
        {
            setSnackbar({ children: "Have to choose Name For Rule", severity: 'error' });
            return;
        }
        if (Utils.check_rule_name(ruleName) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_xor_discount_rule(discountChosenMax1, discountChosenMax2, store_id, ruleName);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    const add_complex_plus_discount_rule = async(values) => {
        const rule_name = values[0];
        console.log(values[0]);
        setRuleName(values[0]);
        if (predictChosenOr1 == null || predictChosenOr2==null)
        {
            setSnackbar({ children: "Have to choose Predict", severity: 'error' });
            return;
        }
        if (ruleName == null)
        {
            setSnackbar({ children: "Have to choose Name For Rule", severity: 'error' });
            return;
        }
        if (Utils.check_rule_name(ruleName) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_xor_discount_rule(discountChosenPlus1, discountChosenPlus2, store_id, ruleName);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    
    return (
        <>

            <Box sx={{ flexGrow: 1 }}>

            <h1 class="Header" align="center">
                            </h1>
            <h3 class="Header" align="center">
            Add Discount Rule
                            </h3>
            </Box>
            
            <Grid container spacing={3} justifyContent="center" alignItems="center">

                <Grid item>
                    <div>Select Type Of Discount Rule</div>
                <Row><select name="option"  onChange={handleInputChange} required>
                        <option value="Simple">Simple</option>
                        <option value="Complex">Complex</option>
                        <option value="Complex And">Complex And</option>
                        <option value="Complex Or">Complex Or</option>
                        <option value="Complex Xor">Complex Xor</option>
                        <option value="Complex Max">Complex Max</option>
                        <option value="Complex Plus">Complex Plus</option>

              
                    </select>
                    {option ==="Simple" ? <div> For </div> : null}
                    {option == "Simple" ? <select name="simpleOptions" value={simpleOptions} onChange={handleInputChange_simple} required>
                        <option value="Store">Store</option>
                        <option value="Category">Category</option>
                        <option value="Product">Product</option>


                    </select>
                    : null}</Row>


                    
                </Grid>
                <Grid item>
                    {option ==="Complex"  ? <ControlledRadioButtonsGroup list={discounts} name="Predicts" save={save_predict}></ControlledRadioButtonsGroup> : null}
                    {option ==="Complex And"  ? <ControlledRadioButtonsGroup list={discounts} name="Predicts" save={save_predictAnd1}></ControlledRadioButtonsGroup> : null}
                    {option ==="Complex Or"  ? <ControlledRadioButtonsGroup list={discounts} name="Predicts" save={save_predictOr1}></ControlledRadioButtonsGroup> : null}
                    {option ==="Complex Xor"  ? <ControlledRadioButtonsGroup list={discounts} name="Discounts" save={save_discountXor1}></ControlledRadioButtonsGroup> : null}
                    {option ==="Complex Max"  ? <ControlledRadioButtonsGroup list={discounts} name="Discounts" save={save_discountMax1}></ControlledRadioButtonsGroup> : null}
                    {option ==="Complex Plus"  ? <ControlledRadioButtonsGroup list={discounts} name="Discounts" save={save_discountPlus1}></ControlledRadioButtonsGroup> : null}
                </Grid>
                <Grid item>
                    {option ==="Complex"  ?<ControlledRadioButtonsGroup list={discounts} name="Discount Rules" save={save_discount_rule}></ControlledRadioButtonsGroup>: null}
                    {option ==="Complex And"  ?<ControlledRadioButtonsGroup list={discounts} name="Discounts" save={save_predictAnd2}></ControlledRadioButtonsGroup>: null}
                    {option ==="Complex Or"  ?<ControlledRadioButtonsGroup list={discounts} name="Discounts" save={save_predictOr2}></ControlledRadioButtonsGroup>: null}
                    {option ==="Complex Xor"  ?<ControlledRadioButtonsGroup list={discounts} name="Discounts" save={save_discountXor2}></ControlledRadioButtonsGroup>: null}
                    {option ==="Complex Max"  ?<ControlledRadioButtonsGroup list={discounts} name="Discounts" save={save_discountMax2}></ControlledRadioButtonsGroup>: null}
                    {option ==="Complex Plus"  ?<ControlledRadioButtonsGroup list={discounts} name="Discounts" save={save_discountPlus2}></ControlledRadioButtonsGroup>: null}

                </Grid>
                
         
                <Grid item>
                {option ==="Simple"  && simpleOptions === "Category"  ? <ControlledRadioButtonsGroup list={categories} name="Categories" save={save_category}></ControlledRadioButtonsGroup> : null}  
                </Grid>
                <Grid item>

                </Grid>
                <Grid item>
                    
                    {option == "Simple" && simpleOptions === "Store" ? <FormDialog fields={add_simple_discount_rule_for_store_fields} getValues={add_simple_discount_rule_for_store} name="Add Simple Discount Rule For Store"></FormDialog>: null}
                    {option == "Simple" && simpleOptions === "Category" && categoryChosen!==null ? <FormDialog fields={add_simple_discount_rule_for_category_fields} getValues={add_simple_discount_rule_for_category} name="Add Simple Discount Rule For Category"></FormDialog>: null}
                    {option == "Simple" && simpleOptions === "Product" ? <FormDialog fields={add_simple_discount_rule_for_product_fields} getValues={add_simple_discount_rule_for_product} name="Add Simple Discount Rule For Product"></FormDialog>: null}
                    {option == "Complex" && predictChosen!==null && discountChosen!==null ? <FormDialog fields={add_complex_discount_rule_fields} getValues={add_complex_discount_rule} name="Add Complex Discount Rule"></FormDialog> : null}
                    {option == "Complex And" && predictChosenAnd1!==null && predictChosenAnd2!==null ? <FormDialog fields={add_complex_and_discount_rule_fields} getValues={add_complex_and_discount_rule} name="Add Complex And Discount Rule"></FormDialog> : null}
                    {option == "Complex Or" && predictChosenOr1!==null && predictChosenOr2!==null ? <FormDialog fields={add_complex_or_discount_rule_fields} getValues={add_complex_or_discount_rule} name="Add Complex Or Discount Rule"></FormDialog> : null}
                    {option == "Complex Xor" && discountChosenXor1!==null && discountChosenXor2!==null ? <FormDialog fields={add_complex_xor_discount_rule_fields} getValues={add_complex_xor_discount_rule} name="Add Complex Xor Discount Rule"></FormDialog> : null}
                    {option == "Complex Max" && discountChosenMax1!==null && discountChosenMax2!==null ? <FormDialog fields={add_complex_max_discount_rule_fields} getValues={add_complex_max_discount_rule} name="Add Complex Max Discount Rule"></FormDialog> : null}
                    {option == "Complex Plus" && discountChosenPlus1!==null && discountChosenPlus2!==null ? <FormDialog fields={add_complex_plus_discount_rule_fields} getValues={add_complex_plus_discount_rule} name="Add Complex Plus Discount Rule"></FormDialog> : null}
                       
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
                    {option ==="Simple"  && simpleOptions === "Product"  ? <StoreProductsTable
            store_id={store_id}
          ></StoreProductsTable> : null}

        </>
    );
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
























































