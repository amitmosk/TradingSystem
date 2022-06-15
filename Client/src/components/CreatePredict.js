
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { StoreApi } from '../API/StoreApi';
import { useParams } from 'react-router-dom';
import { Utils } from '../ServiceObjects/Utils';
import { useEffect } from 'react';
import React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import { PoliciesApi } from '../API/PoliciesApi';
import ControlledRadioButtonsGroup from './ControlledRadioButtonsGroup';
import StoreProductsTable from './StoreProductsTable';
import AddPredictFormDialog from './AddPredictFormDialog';

export default function CreatePredict() {
    const {id} = useParams();
    const store_id =id;
    const add_predict_for_category_fields =["Rule Name"];
    const add_predict_for_time_fields =["Rule Name"];
    const add_predict_for_quantity_fields =["Rule Name", "Quantity"];
    const add_predict_for_product_fields =["Rule Name","Product ID"];
    const add_predict_for_price_fields =["Rule Name", "Price"];
    const add_predict_for_age_fields =["Rule Name", "Age"];





    //API 
    const policiesApi = new PoliciesApi();
    const storeApi = new StoreApi();

    // list of categories, Predicts, discounts

    const [categories,setCategories ] =React.useState(["car1", "cat2", "cat3"]);
    const get_categories_of_stores = async () =>
    {
        const response = await storeApi.get_all_categories(store_id);
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
            if(response.value.length !== 0)
                setCategories(response.value);
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });

        }
    }
    
    useEffect(()=>{get_categories_of_stores()}, []);


    const [categoryChosen, setCategoryChosen] = React.useState(null);
    const save_category = (category) =>{
        console.log(category);
        setCategoryChosen(category);
    }

    const [checked, setChecked] = React.useState(true);
    const [ruleName, setRuleName] = React.useState(undefined);
    
    const handleChange = (event) => {setChecked(event.target.checked)};
    
    
    
    const [option, setOption] = React.useState("Category");
    
    
    const handleCloseSnackbar = () => setSnackbar(null);
    const handleInputChange = event => {
        const name = event.target.name
        const value = event.target.value;
        console.log(name);
        console.log(value);
        setOption(value.toString());  };

    const [snackbar, setSnackbar] = React.useState(null);
    const hadleSubmit = async () => {}


    //Functions To Server
    //    add_predict(store_id,catgorey,product_id,above,equql,num,price,quantity,age,time,year,month,day,name){
    const add_predict_for_category = async(values) => {
        console.log(values);
        const rule_name = values[0];
        const inside = values[1];
        console.log(inside);
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            
            return;
        }
        
        const response = await policiesApi.add_predict(store_id,categoryChosen, -1, 0, inside, 0, 0, 0, 0, 0, 0, 0, 0, rule_name )
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
            window.location.reload();
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    const add_predict_for_time = async(values) => {
        console.log(values);
        const rule_name = values[0];
        const date = values[1].split('-');
        console.log(date);
        const year = date[0];
        const month = date[1];
        const day = date[2];
        const inside = values[1];
        console.log(inside);
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_predict(store_id,"", -1, 0, inside, 0, 0, 0, 0, 1,parseInt(year) ,parseInt(month) ,parseInt(day) , rule_name )
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
            window.location.reload();
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }
    const add_predict_for_product = async(values) => {
        const rule_name = values[0];
        const product_id = values[1];
        const inside = values[2];
        console.log(inside);
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }//TODO: chek product id with products list
        if (Utils.check_all_digits(product_id)==0 || Utils.check_range(parseInt(product_id)) == 0)
        {
            setSnackbar({ children: "Illegal Range", severity: 'error' });
            return;
        }
        const response = await policiesApi.add_predict(store_id,"", product_id, 0, inside, 0, 0, 0, 0, 0, 0, 0, 0, rule_name )
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
            window.location.reload();
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }


    //range:
    // 1-> Above
    // 0-> Equal
    // 2-> Below

//add_predict(store_id,catgorey,product_id,above,equql,num,price,quantity,age,time,year,month,day,name){

    const add_predict_for_quantity = async(values) => {
        console.log(values);
        const rule_name = values[0];
        const value=values[1];
        const range = values[2];
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        if (Utils.check_all_digits(value)==0 || Utils.check_range(parseInt(value)) == 0)
        {
            setSnackbar({ children: "Illegal Range", severity: 'error' });
            return;
        }
        if(range !== 0 && range!==1 && range!==2)
        {
            return;
        }
        let response;
        if(range==0)
        {
            response = await policiesApi.add_predict(store_id,"", -1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, rule_name )
            if(!response.was_exception)
            {
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();

            }
            else
            {
                setSnackbar({ children: response.message, severity: 'error' });

            }
        }
        else if(range==1)
        {
            response = await policiesApi.add_predict(store_id,"", -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, rule_name );
            if(!response.was_exception)
            {
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();

            }
            else
            {
                setSnackbar({ children: response.message, severity: 'error' });

            }
        }
        else if(range==2)
        {
            response = await policiesApi.add_predict(store_id,"", -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, rule_name );
            if(!response.was_exception)
            {
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();

            }
            else
            {
                setSnackbar({ children: response.message, severity: 'error' });

            }
        }
        if (!response.was_exception)
        {
            setSnackbar({ children: response.message, severity: 'success' });
        }
        else
        {
            setSnackbar({ children: response.message, severity: 'error' });
        }
    }

    const add_predict_for_price = async(values) => {
        console.log(values);
        const rule_name = values[0];
        const value=values[1];
        const range = values[2];
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        if (Utils.check_all_digits(value)==0 || Utils.check_range(parseInt(value)) == 0)
        {
            setSnackbar({ children: "Illegal Range", severity: 'error' });
            return;
        }
        if(range !== 0 && range!==1 && range!==2)
        {
            return;
        }
        let response;
        if(range==0)
        {
            response = await policiesApi.add_predict(store_id,"", -1, 0, 1, value, 1, 0, 0, 0, 0, 0, 0, rule_name );
            if(!response.was_exception)
            {
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();

            }
            else
            {
                setSnackbar({ children: response.message, severity: 'error' });

            }
        }
        else if(range==1)
        {
            response = await policiesApi.add_predict(store_id,"", -1, 1, 0, value, 1, 0, 0, 0, 0, 0, 0, rule_name );
            if(!response.was_exception)
            {
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();

            }
            else
            {
                setSnackbar({ children: response.message, severity: 'error' });

            }
        }
        else if(range==2)
        {
            response = await policiesApi.add_predict(store_id,"", -1, 0, 0, value, 1, 0, 0, 0, 0, 0, 0, rule_name );
            if(!response.was_exception)
            {
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();

            }
            else
            {
                setSnackbar({ children: response.message, severity: 'error' });

            }
        }
       
    }

    const add_predict_for_age= async(values) => {
        console.log(values);
        const rule_name = values[0];
        const age=values[1];//age
        const range = values[2];
        if (Utils.check_rule_name(rule_name) == 0)
        {
            setSnackbar({ children: "Illegal Rule Name", severity: 'error' });
            return;
        }
        if (Utils.check_all_digits(age)==0 || Utils.check_range(parseInt(age)) == 0)
        {
            setSnackbar({ children: "Illegal Range", severity: 'error' });
            return;
        }
        if(range !== 0 && range!==1 && range!==2)
        {
            return;
        }
        let response;
        if(range==0)
        {
            response = await policiesApi.add_predict(store_id,"", -1, 0, 1, age, 0, 0, 1, 0, 0, 0, 0, rule_name );
            if(!response.was_exception)
            {
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();

            }
            else
            {
                setSnackbar({ children: response.message, severity: 'error' });

            }
        }
        else if(range==1)
        {
            response = await policiesApi.add_predict(store_id,"", -1, 1, 0, age, 0, 0, 1, 0, 0, 0, 0, rule_name );
            if(!response.was_exception)
            {
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();

            }
            else
            {
                setSnackbar({ children: response.message, severity: 'error' });

            }
        }
        else if(range==2)
        {
            response = await policiesApi.add_predict(store_id,"", -1, 0, 0, age, 0, 0, 1, 0, 0, 0, 0, rule_name );
            if(!response.was_exception)
            {
                setSnackbar({ children: response.message, severity: 'success' });
                window.location.reload();

            }
            else
            {
                setSnackbar({ children: response.message, severity: 'error' });

            }
        }
        
    }
    
    
    return (
        <>

            <Box sx={{ flexGrow: 1 }}>

            <h1 class="Header" align="center">
                            </h1>
            <h2 class="Header" align="center">
            Create Predict
                            </h2>
                            <h5 class="Header" align="center">
                            Select Type Of Predict
                            </h5>
            </Box>
            
            <Grid container spacing={3} justifyContent="center" alignItems="center">

                <Grid item>
                <select name="option"  onChange={handleInputChange} required>
                        <option value="Category">Category</option>
                        <option value="Age">Age</option>
                        <option value="Product">Product</option>
                        <option value="Price">Price</option>
                        <option value="Quantity">Quantity</option>
                        <option value="Time">Time</option>

              
                    </select>
                    


                    
                </Grid>
                <Grid item>
                   
                </Grid>
                <Grid item>
                    

                </Grid>
                
         
                <Grid item>
                {option ==="Category"  ? <ControlledRadioButtonsGroup list={categories} name="Categories" save={save_category}></ControlledRadioButtonsGroup> : null}  
                
                </Grid>
                <Grid item>

                </Grid>
                <Grid item>
                    
                    {option == "Category" && categoryChosen!==null ? <AddPredictFormDialog fields={add_predict_for_category_fields} head={"Please Choose Inside/Outside the Category"} option1={"Inside"} option2={"Outside"} getValues={add_predict_for_category} name={"Add Predict For Category"}></AddPredictFormDialog>: null}
                    {option == "Time" ? <AddPredictFormDialog fields={add_predict_for_time_fields} head={"Please Choose Include/Uninclude the Time"} option1={"Include"} option2={"Uninclude"} getValues={add_predict_for_time} name={"Add Predict For Time"} time={1}></AddPredictFormDialog>: null}
                    {option == "Product"  ? <AddPredictFormDialog fields={add_predict_for_product_fields} head={"Please Choose Include/Uninclude the Product"} option1={"Include"} option2={"Uninclude"} getValues={add_predict_for_product} name={"Add Predict For Product"}></AddPredictFormDialog>: null}
                    {option == "Quantity" ? <AddPredictFormDialog fields={add_predict_for_quantity_fields} head={"Please Choose Range Of Quantity"} option1={"Above"} option2={"Equal"} option3={"Below"} getValues={add_predict_for_quantity} name={"Add Predict For Quantity"}></AddPredictFormDialog>: null}
                    {option == "Price" ? <AddPredictFormDialog fields={add_predict_for_price_fields} head={"Please Choose Range Of Price"} option1={"Above"} option2={"Equal"} option3={"Below"} getValues={add_predict_for_price} name={"Add Predict For Price"}></AddPredictFormDialog>: null}
                    {option == "Age" ? <AddPredictFormDialog fields={add_predict_for_age_fields} head={"Please Choose Range Of Age"} option1={"Above"} option2={"Equal"} option3={"Below"} getValues={add_predict_for_age} name={"Add Predict For Age"}></AddPredictFormDialog>: null}
                  
                       
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
                     {option ==="Product"  ? <StoreProductsTable
            store_id={store_id}
          ></StoreProductsTable> : null} 

        </>
    );
}


























































