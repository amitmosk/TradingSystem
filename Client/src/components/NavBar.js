import React, { Component } from "react";
import Typography from "@material-ui/core/Typography";
import Link from "@mui/material/Button";
import PrimarySearchAppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import AccountMenu from "./AccountMenu";
import Grid from "@mui/material/Grid";
import { ConnectApi } from "../API/ConnectApi";
import { Navigate } from 'react-router-dom'; 
import HomeIcon from '@mui/icons-material/Home';
import SimpleBadge from "./SimpleBadge";

export default function NavBar({updateUserState, user}) {

  const user_name = user.name;
  console.log("username = " + user_name);
  const logout = async () => {
    let response = await new ConnectApi().logout();
    alert(response.message);
    if (!response.was_exception) {
      console.log(response)
      const user_logout = response.value;
      console.log("in logout - user is "+user_logout);
      updateUserState(user_logout);
      // return (<Navigate to="/"/>)
    }
  };
  const login_register = () => {
    return user.state === 0 ?
     (
      <>
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
      Register
    </Link>
    </>
    ):(<></>)
  }
  return (
    <PrimarySearchAppBar position="relative" style={{ background: "#2E3755" }}>
      <Toolbar>
        <Grid container justifyContent="flex-start">
          <Typography variant="h6" color="inherit" noWrap>
            Hello {user.name}
            {/* Hello {user_name} , */}
            {/* Hello Amit, */}
          </Typography>
        </Grid>
        <Grid container justifyContent="flex-end">
          <Link
              href="/AllStores"
              component="button"
              variant="body2"
              position="right"
            >
              Market Stores
            </Link>



            {/* <MenuItem>
              <Avatar />{" "}
              <Link to={{pathname:`/Notifications`}} onClick={handleClose} underline="hover"> {<SimpleBadge num={6}></SimpleBadge>}</Link>
            </MenuItem> */}

            <Link
            href="/Notifications"
            component="button"
            variant="body2"
            position="right"
            onClick={() => {
              console.info("I'm Register button, add link.");
            }}
          >
            <SimpleBadge num={6}></SimpleBadge>
            
          </Link>




          <Link
            href="/"
            component="button"
            variant="body2"
            position="right"
            onClick={() => {
              console.info("I'm Register button, add link.");
            }}
          >
            <HomeIcon></HomeIcon>
            
          </Link>
          {login_register()}
          <AccountMenu log={logout} state={user.state} user={user}></AccountMenu>
        </Grid>
      </Toolbar>
    </PrimarySearchAppBar>
  );
}
