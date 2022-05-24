

import React, { Component } from 'react';
import Typography from '@material-ui/core/Typography';
import Link from '@mui/material/Button';
import PrimarySearchAppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import AccountMenu from './AccountMenu';


export default function NavBar({state}){
    const user_name = state.name;
    console.log(user_name);
     const logout = async() => { 
        console.log("in logout home page");
        let response = await this.connectAPI.logout();
        
        alert(response.message);
        if (!response.was_exception)
        {
            const user = response.value;
            this.props.updateUserState(user);
        }
      }
   return( <PrimarySearchAppBar position="relative" color="white">
                    <Toolbar>
                        <Typography variant="h6" color="inherit" noWrap>
                            Hello {state.name} ,
                            {/* Hello {user_name} , */}
                            {/* Hello Amit, */}
                        </Typography>

                        <Link
                            href="/Login"
                            component="button"
                            variant="body2"
                            position="right"
                            onClick={() => {
                                console.info("I'm Login button, add link.");
                            }}
                        >
                            Login
                        </Link>
                        <Link
                            href="/Register"
                            component="button"
                            variant="body2"
                            position="right"
                            onClick={() => {

                                console.info("I'm Register button, add link.");
                            }}
                        >
                            \ Register
                        </Link>
                        <AccountMenu log={logout}></AccountMenu>
                    </Toolbar>

                </PrimarySearchAppBar>)
}