
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { StoreApi } from '../API/StoreApi';
import { useParams } from 'react-router-dom';
import { Utils } from '../ServiceObjects/Utils';
import { useEffect } from 'react';
import React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import FormDialog from './FormDialog';
import { PoliciesApi } from '../API/PoliciesApi';
import ControlledRadioButtonsGroup from './ControlledRadioButtonsGroup';


export default function AddPurchase() {
    const {id} = useParams();
    const store_id =id;

    //fields

    const add_simple_purchase_rule_for_store_fields=["Rule Name"];
    const add_complex_and_policy_rule_fields=["Rule Name"];
    const add_complex_or_policy_rule_fields=["Rule Name"];
   
    //API 
    const policiesApi = new PoliciesApi();
    const storeApi = new StoreApi();

    // list of categories, Predicts, discounts

    const [purchases,setPurchases] =React.useState([]);
    const [predicts,setPredicts] =React.useState([]);
    const get_purchases_predict_lists = async () =>
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
        const response_purchases = await policiesApi.get_purchase_policy(store_id);
        if (!response_purchases.was_exception)
        {
            setSnackbar({ children: response_purchases.message, severity: 'success' });
                setPurchases(response_purchases.value);
        }
        else
        {
            setSnackbar({ children: response_purchases.message, severity: 'error' });

        }
    }
    useEffect(()=>{get_purchases_predict_lists()}, []);  

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
    const [policyChosenAnd1, setPolicyChosenAnd1] = React.useState(null);
    const [policyChosenAnd2, setPolicyChosenAnd2] = React.useState(null);

    const save_policyAnd1 = (policy) =>{
        console.log(policy);
        setPolicyChosenAnd1(policy);
    }
    const save_policyAnd2 = (policy) =>{
        console.log(policy);
        setPolicyChosenAnd2(policy);
    }
    //------------- complex or----------------------

    const [policyChosenOr1, setPolicyChosenOr1] = React.useState(null);
    const [policyChosenOr2, setPolicyChosenOr2] = React.useState(null);

    const save_policyOr1 = (policy) =>{
        console.log(policy);
        setPolicyChosenOr1(policy);
    }
    const save_policyOr2 = (policy) =>{
        console.log(policy);
        setPolicyChosenOr2(policy);
    }

   
    
    
    
    
    
    const [option, setOption] = React.useState("Simple");
    
    
    const handleCloseSnackbar = () => setSnackbar(null);
    const handleInputChange = event => {
        const name = event.target.name
        const value = event.target.value;
        console.log(name);
        console.log(value);
        setOption(value.toString()); };
    
    const [snackbar, setSnackbar] = React.useState(null);
    const hadleSubmit = async () => {}



    // add_simple_purchase_rule(PredictName,  NameOfRule,  store_id){
    // add_and_purchase_rule( left,  right,  store_id,  NameOfrule){
    // add_or_purchase_rule( left,  right,  store_id,  NameOfrule){

    //Functions To Server
    const add_simple_purchase_rule = async (values) => {
        const rule_name = values[0];
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        
        const response = await policiesApi.add_simple_purchase_rule(predictChosen, rule_name, store_id); 
        if (!response.was_exception)
        {
            window.location.reload();
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }

    }
    const add_and_purchase_rule = async (values) => {
        const rule_name = values[0];
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
    
        const response = await policiesApi.add_and_purchase_rule(policyChosenAnd1, policyChosenAnd2, store_id, rule_name); 
        if (!response.was_exception)
        {
            window.location.reload();
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    const add_or_purchase_rule = async (values) => {
        const rule_name = values[0];
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_or_purchase_rule(policyChosenOr1, policyChosenOr2,store_id, rule_name); 
        if (!response.was_exception)
        {
            window.location.reload();
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
            <h2 class="Header" align="center">
            Add Purchase Rule
                            </h2>
                            <h5 class="Header" align="center">
                            Select Type Of Purchase Rule
                            </h5>
                            
            </Box>
            
            <Grid container spacing={3} justifyContent="center" alignItems="center" paddingTop={8}> 

                <Grid item>
                    <select name="option"  onChange={handleInputChange} required>
                        <option value="Simple">Simple</option>
                        <option value="Complex And">Complex And</option>
                        <option value="Complex Or">Complex Or</option>

              
                    </select>
                 


                    
                </Grid>
                <Grid item>
                    {option ==="Simple"  ? <ControlledRadioButtonsGroup list={predicts} name="Predicts" save={save_predict}></ControlledRadioButtonsGroup> : null}
                    {option ==="Complex And"  ? <ControlledRadioButtonsGroup list={purchases} name="Purchases" save={save_policyAnd1}></ControlledRadioButtonsGroup> : null}
                    {option ==="Complex Or"  ? <ControlledRadioButtonsGroup list={purchases} name="Purchases" save={save_policyOr1}></ControlledRadioButtonsGroup> : null}
                    
                </Grid>
                <Grid item>
                    {option ==="Complex And"  ?<ControlledRadioButtonsGroup list={purchases} name="Purchases" save={save_policyAnd2}></ControlledRadioButtonsGroup>: null}
                    {option ==="Complex Or"  ?<ControlledRadioButtonsGroup list={purchases} name="Purchases" save={save_policyOr2}></ControlledRadioButtonsGroup>: null}
                    

                </Grid>
                
         
                <Grid item>
                </Grid>
                <Grid item>

                </Grid>
                <Grid item>
                    
                    {option == "Simple"  ? <FormDialog fields={add_simple_purchase_rule_for_store_fields} getValues={add_simple_purchase_rule} name="Add Simple Purchase Rule"></FormDialog>: null}
                    {option == "Complex And" && policyChosenAnd1!==null && policyChosenAnd2!==null ? <FormDialog fields={add_complex_and_policy_rule_fields} getValues={add_and_purchase_rule} name="Add Complex And Purchase Rule"></FormDialog> : null}
                    {option == "Complex Or" && policyChosenOr1!==null && policyChosenOr2!==null ? <FormDialog fields={add_complex_or_policy_rule_fields} getValues={add_or_purchase_rule} name="Add Complex Or Purchase Rule"></FormDialog> : null}
                   
                       
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

























































