import { Component } from 'react';
import './App.css';
import {BrowserRouter, Link, Route, Router, Routes} from "react-router-dom";

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
// function App() {
//   return <Button variant="contained">Hello World</Button>;
// }

export default class App extends Component {
  static displayName = App.name;

  constructor(props) {
      super(props)
      this.state = {
          isLoggedIn: false,
          ownedStoreList:[],
          email:'',
          role: undefined,

          messages: [],
          webSocketConnection: undefined
      }
 

  }
      

  
    
    async updateLoginHandler(email){
      console.log("in updateLoginHandler\n\n\n\n\n");
      this.setState({
          isLoggedIn: true,
          email: email,
          role: "",
      })
  }
 


  render () {
    return (
      <BrowserRouter>
      <Routes>
      {/* <Route path="/" element={<Album />}></Route> */}
      <Route path="/" element={<Login isLoggedIn={this.state.isLoggedIn} loginUpdateHandler={this.updateLoginHandler}/>}></Route>
      <Route path="/Register" element={<Register />}></Route>
      <Route path="/HomePageSearch" element={<HomePageSearch />}></Route>
      <Route path="/StorePage" element={<StorePage store_id=""/>}></Route>
      <Route path="/AdminSendMessage" element={<AdminSendMessage/>}></Route>
      
    
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

