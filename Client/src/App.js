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
import Album from './components/Album';
import { ConnectApi } from './API/ConnectApi';
// function App() {
//   return <Button variant="contained">Hello World</Button>;
// }

export default class App extends Component {
  static displayName = App.name;

  constructor(props) {
    super(props)
    this.state = {
      isLoggedIn: false,
      ownedStoreList: [],
      email: '',
      name: "Guest",
      role: undefined,

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
        isLoggedIn: false,
        name: "Geust",
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
      isLoggedIn: true,
    })
  }
  async updateRegisterHandler(name, email, user) {
    console.log("in updateRegisterHandler\n\n\n\n\n");
    this.setState({
      isLoggedIn: true,
      name: name,
      email: email,
      user:user
    })
  }



  render() {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Album user_name = {this.state.name} />}></Route>
          <Route path="/Login" element={<Login isLoggedIn={this.state.isLoggedIn} updateLoginHandler={this.updateLoginHandler.bind(this)} />}></Route>
          <Route path="/Register" element={<Register updateRegisterHandler={this.updateRegisterHandler.bind(this)} />}></Route>
          <Route path="/HomePageSearch" element={<HomePageSearch />}></Route>
          <Route path="/StorePage" element={<StorePage store_id="" />}></Route>
          <Route path="/AdminSendMessage" element={<AdminSendMessage />}></Route>
          <Route path="/AdminPage" element={<AdminPage />}></Route>
          <Route path="/ShoppingCart" element={<ShoppingCart />}></Route>
          {/* <Route path="/" element={<StoreManagment />}></Route> */}


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

