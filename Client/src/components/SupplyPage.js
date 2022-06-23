import React, { Component } from 'react';
import Button from '@mui/material/Button';
import Link from '@mui/material/Button';
import HomeIcon from '@mui/icons-material/Home';
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert"; 
import { Supply } from '../ServiceObjects/Supply';
import { Utils } from '../ServiceObjects/Utils';

export default function SupplyPage({update}) {
    const [snackbar, setSnackbar] = React.useState(null);
    const handleCloseSnackbar = () => setSnackbar(null);

    const [name, setName] = React.useState("");
    const [address, setAddress] = React.useState("");
    const [city, setCity] = React.useState("");
    const [country, setCountry] = React.useState("");
    const [zip, setZip] = React.useState("");



    const check_supply = async () =>{
        console.log(name);
        console.log(address);
        console.log(city);
        console.log(country);
        console.log(zip);
        if (Utils.check_holder(name)== 0)
        {
            setSnackbar({ children: "Illegal Name", severity: 'error' });
            return;
        }
        if (Utils.check_address(address)== 0)
        {
            setSnackbar({ children: "Illegal address", severity: 'error' });
            return;
        }
        if (Utils.check_city(city)== 0)
        {
            setSnackbar({ children: "Illegal city", severity: 'error' });
            return;
        }
        if (Utils.check_country(country)== 0)
        {
            setSnackbar({ children: "Illegal country", severity: 'error' });
            return;
        }
        if (Utils.check_zip(zip)== 0)
        {
            setSnackbar({ children: "Illegal zip code", severity: 'error' });
            return;
        }
        

        const supply_info = Supply.create(name, address, city, country, zip);
        update(supply_info);
    }
    

            return (
                <main class="LoginMain">
                    <div class="LoginWindow">
                        <h3>Supply</h3>
                        <form class="LoginForm" >
                           
                            <input type="name" name="name" onChange={e => setName(e.target.value)}
                                    placeholder="Name" required/>
                            <input type="address" name="address" onChange={e => setAddress(e.target.value)}
                                    placeholder="Address" required/>
                            <input type="city" name="city" onChange={e => setCity(e.target.value)}
                                    placeholder="City" required/>
                            <input type="country" name="country"  onChange={e => setCountry(e.target.value)}
                                    placeholder="Country" required/>
                            <input type="zip" name="zip"  onChange={e => setZip(e.target.value)}
                                    placeholder="Zip" required/>
                          
                            <div className="ConnectRegister">
                                
                                <Button onClick={check_supply} variant="contained">Confirm</Button>
                            </div>
                        </form>
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
                    </div>
                </main>
            );
        
    }
