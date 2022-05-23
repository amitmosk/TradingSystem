import { Component } from 'react';
import './App.css';
import { BrowserRouter, Link, Route, Router, Routes } from "react-router-dom";

import Login from './components/Login';
import HomePageSearch from './components/HomePageSearch';
import Register from './components/Register';
import Payment from './components/Payment';
import Supply from './components/Supply';
// import * as React from 'react';
// import ReactDOM from 'react-dom';
import Button from '@mui/material/Button';
import StorePage from './components/StorePage';
import ShoppingCart from './components/ShoppingCart';
import AdminSendMessage from './components/AdminSendMessage';
import AdminPage from './components/AdminPage';
import StoreManagment from './components/StoreManagment';
import HomePage from './components/HomePage';
import { ConnectApi } from './API/ConnectApi';
import AddDiscount from './components/AddDiscount';
import EditProfile from './components/EditProfile';

// user state enum
const GUEST = 0;
const ASSIGN_USER = 1;
const ADMIN = 2;

export default class App extends Component {
  static displayName = App.name;

  constructor(props) {
    super(props)
    this.state = {
      user_state : GUEST,
      ownedStoreList: [],
      email: '',
      name: "Guest",
      user:undefined,

      messages: [],
      webSocketConnection: undefined
    }
    this.connectApi = new ConnectApi();


  }
  static async logout(){
    console.log("in logout");
    let response = await this.connectApi.logout();
    if (!response.was_exception)
    {
      this.setState({
        user_state: GUEST,
        name: "Guest",
        email:"",
        user:undefined,
      })
    }
    else
    {

    }
  }




  async updateLoginHandler() {
    console.log("in updateLoginHandler\n\n\n\n\n");
    this.setState({
      user_state: ASSIGN_USER
        })
  }
  async updateRegisterHandler(name, email, user) {
    console.log("in updateRegisterHandler\n\n\n\n\n");
    this.setState({
      user_state: ASSIGN_USER,
      name: name,
      email: email,
      user:user
    })
  }



  render() {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage user_name = {this.state.name} />}></Route>
          <Route path="/Login" element={<Login isLoggedIn={this.state.isLoggedIn} updateLoginHandler={this.updateLoginHandler.bind(this)} />}></Route>
          <Route path="/Register" element={<Register updateRegisterHandler={this.updateRegisterHandler.bind(this)} />}></Route>
          <Route path="/HomePageSearch" element={<HomePageSearch />}></Route>
          <Route path="/StorePage" element={<StorePage store_id="" />}></Route>
          <Route path="/AdminSendMessage" element={<AdminSendMessage />}></Route>
          <Route path="/AdminPage" element={<AdminPage />}></Route>
          <Route path="/ShoppingCart" element={<ShoppingCart />}></Route>
          <Route path="/StoreManagment" element={<StoreManagment store_id=""/>}></Route>
          <Route path="/AddDiscount" element={<AddDiscount store_id=""/>}></Route>
          <Route path="/EditProfile" element={<EditProfile></EditProfile>}></Route>

        </Routes>
      </BrowserRouter>





      //   <div> 
      //   {/* <HomePageSearch /> 
      //   <Payment/>
      //   <Supply/>
      //   <Register/>    */}
      //   <Login isLoggedIn={this.state.isLoggedIn} loginUpdateHandler={this.updateLoginHandler}/>
      //   {/* <StorePage/> */}

      //   {/* <ShoppingCart/> */}
      // </div>
    );
  }
}

