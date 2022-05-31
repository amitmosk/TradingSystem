import { Component } from "react";
import "./App.css";
import { BrowserRouter, Link, Route, Router, Routes } from "react-router-dom";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";

import Login from "./components/Login";
import HomePageSearch from "./components/HomePageSearch";
import Register from "./components/Register";
import Payment from "./components/Payment";
import Supply from "./components/Supply";
// import * as React from 'react';
// import ReactDOM from 'react-dom';
import Button from "@mui/material/Button";
import StorePage from "./components/StorePage";
import ShoppingCart from "./components/ShoppingCart";
import AdminSendMessage from "./components/AdminSendMessage";
import AdminPage from "./components/AdminPage";
import StoreManagment from "./components/StoreManagment";
import { ConnectApi } from "./API/ConnectApi";
import { UserApi } from "./API/UserApi";
import AddDiscount from "./components/AddDiscount";
import EditProfilePremium from "./components/EditProfilePremium";
import EditProfile from "./components/EditProfile";
import ProductPage from "./components/ProductPage";
import HomePage from "./components/HomePage";
import { User } from "./ServiceObjects/User";
import ViewStat from "./components/ViewStat";
import ProductPageNevigator from "./components/ProductPageNevigator";
import AdminViewUserQuestions from "./components/AdminViewUserQuestions";
import ManagerViewStoreQuestions from "./components/ManagerViewStoreQuestions";
// import UserPurchaseHistory from './components/UserPurchaseHistory';
// import UserQuestions from './components/UserQuestions';
import UserViewQuestions from "./components/UserViewQuestions";
import ViewStaffInformation from "./components/ViewStaffInformation";
import ViewStorePurchaseHistory from "./components/ViewStorePurchaseHistory";
import ViewUserPurchaseHistory from "./components/ViewUserPurchaseHistory";
import MangerViewStoreQuestionsNevigator from "./components/MangerViewStoreQuestionsNevigator";
import NavBar from "./components/NavBar";
import StorePageNevigator from "./components/StorePageNevigator";
import StoreManagmentNevigator from "./components/StoreManagmentNevigator";
import ViewStaffInformationNevigator from "./components/ViewStaffInformationNevigator";
import ViewStorePurchaseHistoryNevigator from "./components/ViewStorePurchaseHistoryNevigator";
import AllStores from "./components/AllStores";
import MyStoresNevigator from "./components/MyStoresNevigator";

export default class App extends Component {
  static displayName = App.name;
  constructor(props) {
    super(props);
    this.state = {
      user:User.guest(),
      messages: [],
      snackbar: null,
      webSocketConnection: undefined,
    };
    this.connectApi = new ConnectApi();
    this.get_online_user()
    this.updateUserState = this.updateUserState.bind(this);
  }

  async updateUserState(user) {
    console.log("in updateUserState\n\n\n\n\n");
    console.log(user);
    console.log(this.state.user);
    this.setState({
      // Important: read `state` instead of `this.state` when updating.
      user: user,
    });

    console.log(this.state.user);
  }

  // async updateUserStateName(name) {
  //   console.log("in set UserState - name\n\n\n\n\n");
  //   this.setState({
  //     user.name: name,
  //     })
  //   }

  get_state() {
    return this.state.user;
  }

  async get_online_user(){
      let response = await this.connectApi.get_online_user()
      this.setState({user : response.value})
  }

  render() {
    return (
      <BrowserRouter>
        <NavBar
          state={this.state.user}
          updateUserState={this.updateUserState.bind(this)}
          user = {this.state.user}
        ></NavBar>

        <Routes>
          <Route
            path="/"
            element={
              <HomePage
                user={this.state.user}
                updateUserState={this.updateUserState.bind(this)}
              />
            }
          ></Route>
          <Route
            path="/Login"
            element={
              <Login
                updateUserState={this.updateUserState.bind(this)}
                state={this.state.user.state}
              />
            }
          ></Route>
          <Route
            path="/Register"
            element={
              <Register updateUserState={this.updateUserState.bind(this)} />
            }
          ></Route>
          <Route path="/HomePageSearch" element={<HomePageSearch />}></Route>
          {/* <Route path="/StorePage" element={<StorePageNevigator/>}></Route> */}
          <Route path="/StorePage/:id" element={<StorePageNevigator />}></Route>
          <Route path="/AllStores/StorePage/:id" element={<StorePageNevigator />}></Route>
          <Route
            path="/AdminSendMessage"
            element={<AdminSendMessage />}
          ></Route>
          <Route path="/AdminPage" element={<AdminPage />}></Route>
          <Route path="/ShoppingCart" element={<ShoppingCart />}></Route>
          <Route
            path="/StorePage/:id/StoreManagment"
            element={<StoreManagmentNevigator />}
          ></Route>
          <Route
            path="/StorePage/:id/StoreManagment/AddDiscount"
            element={<AddDiscount store_id={1} />}
          ></Route>
          <Route
            path="/EditProfile"
            element={<EditProfile get_state={this.get_state.bind(this)} />}
          ></Route>
          <Route
            path="/EditProfilePremium"
            element={
              <EditProfilePremium get_state={this.get_state.bind(this)} />
            }
          ></Route>
          <Route
            path="/StorePage/:store_id/ProductPage/:product_id"
            element={<ProductPageNevigator />}
          ></Route>
          <Route
            path="/AllStores"
            element={<AllStores/>}
          ></Route>
          <Route
            path="/MyStores/:user"
            element={<MyStoresNevigator/>}
          ></Route>

          <Route path="/ProductPage" element={<ProductPage />}></Route>
          {/* <Route exact path="/home/:amit" element={<ProductPage product_id={1} store_id={1}/>}></Route> */}
          <Route path="/ViewStat" element={<ViewStat />}></Route>
          <Route
            path="/AdminViewUserQuestions"
            element={<AdminViewUserQuestions />}
          ></Route>
          <Route
            path="/StorePage/:id/StoreManagment/ManagerViewStoreQuestions"
            element={<MangerViewStoreQuestionsNevigator />}
          ></Route>
          {/* <Route path="/StoreManagment/ManagerViewStoreQuestions/:id" element={<ManagerViewStoreQuestions />}></Route> */}
          {/* <Route path="/UserViewQuestions" element={<UserViewQuestions />}></Route> */}
          <Route
            path="/StorePage/:id/StoreManagment/ViewStaffInformation"
            element={<ViewStaffInformationNevigator />}
          ></Route>
          <Route
            path="/ViewUserPurchaseHistory"
            element={<ViewUserPurchaseHistory />}
          ></Route>

          <Route
            path="/StorePage/:id/StoreManagment/ViewStorePurchaseHistory"
            element={<ViewStorePurchaseHistoryNevigator />}
          ></Route>

          {/* <Route path="/UserPurchaseHistory" element={<UserPurchaseHistory />}></Route> */}
          {/* <Route path="/UserQuestions" element={<?? />}></Route>
          // notificans
          <Route path="/Notifications" element={<?? />}></Route> } */}
        </Routes>

        {!!this.state.snackbar && (
          <Snackbar
            open
            anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
            onClose={this.handleCloseSnackbar}
            autoHideDuration={6000}
          >
            <Alert
              {...this.state.snackbar}
              onClose={this.handleCloseSnackbar}
            />
          </Snackbar>
        )}
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
