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
import { ConnectApi } from './API/ConnectApi';
import { UserApi } from './API/UserApi';
import AddDiscount from './components/AddDiscount';
import EditProfilePremium from './components/EditProfilePremium';
import EditProfile from './components/EditProfile';
import ProductPage from './components/ProductPage';
import HomePage from './components/HomePage';
import {User} from './ServiceObjects/User';
// import ViewUserQuestions from './components/ViewUserQuestions';
import ViewStat from './components/ViewStat';
import UserQuestions from './components/UserQuestions';


export default class App extends Component {
  static displayName = App.name;

  constructor(props) {
    super(props)
    this.state = {
      user: User.guest(),
      messages: [],
      webSocketConnection: undefined
    }
    this.connectApi = new ConnectApi();
    this.updateUserState = this.updateUserState.bind(this);



  }




  async updateUserState(user) {
    console.log("in updateUserState\n\n\n\n\n");
    console.log(user);
    console.log(this.state.user);

    this.setState((state) => {
      // Important: read `state` instead of `this.state` when updating.
      return {user: user}
    });
    
    console.log(this.state.user);
    }


  // async updateUserStateName(name) {
  //   console.log("in set UserState - name\n\n\n\n\n");
  //   this.setState({
  //     user.name: name,
  //     })
  //   }

    get_name() {
      console.log("in get UserState - name\n\n\n\n\n");
      console.log(this.state.user);
      return this.state.user.name;
      }

      get_state() {
        return this.state.user;
      }

// user own questions -> view my questions.
  // async get_user_questions() {
  //     console.log("get user questions\n");
  //     const user_api = new UserApi();
  //     console.log("try 2");
  //     let response = await user_api.get_user_questions();
  //     console.log("try 3");
  //     alert(response.message);
  //     if (!response.was_exception) {
  //         console.log("in get store staff info - success!\n");
  //         console.log(response);
  //         return response.value;
  //     }
  //     else {
  //         return [];

  //     }
  //   }

  render() {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage updateUserState={this.updateUserState.bind(this)}/>}></Route>
          <Route path="/Login" element={<Login updateUserState={this.updateUserState.bind(this)} />}></Route>
          <Route path="/Register" element={<Register updateUserState={this.updateUserState.bind(this)} />}></Route>
          <Route path="/HomePageSearch" element={<HomePageSearch />}></Route>
          <Route path="/StorePage" element={<StorePage store_id="" />}></Route>
          <Route path="/AdminSendMessage" element={<AdminSendMessage />}></Route>
          <Route path="/AdminPage" element={<AdminPage />}></Route>
          {/* <Route path="/ShoppingCart" element={<ShoppingCart />}></Route> */}
          <Route path="/StoreManagment" element={<StoreManagment store_id=""/>}></Route>
          <Route path="/AddDiscount" element={<AddDiscount store_id=""/>}></Route>
          <Route path="/EditProfile" element={<EditProfile get_state={this.get_state.bind(this)} />}></Route>
          <Route path="/EditProfilePremium" element={<EditProfilePremium get_state={this.get_state.bind(this)} />}></Route>

          <Route path="/ProductPage" element={<ProductPage product_id={1} store_id={1}/>}></Route>
          {/* <Route exact path="/home/:amit" element={<ProductPage product_id={1} store_id={1}/>}></Route> */}
          <Route path="/ViewUserQuestions" element={<ViewUserQuestions questions={[]} />}></Route>
          <Route path="/ViewStat" element={<ViewStat />}></Route>


          {/* // user history purchase. */}
           {/* <Route path="/UserPurchaseHistory" element={<UserQuestions questions={this.get_user_questions()}/>}></Route> */}
          {/* // user own questions -> view my questions. */}
          {/* <Route path="/UserQuestions" element={<?? />}></Route>
          // notificans
          <Route path="/Notifications" element={<?? />}></Route> } */}

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

