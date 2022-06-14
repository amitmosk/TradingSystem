import { Component, React } from "react";
import "./App.css";
import { BrowserRouter, Link, Route, Router, Routes } from "react-router-dom";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";

import Login from "./components/Login";
import HomePageSearch from "./components/HomePageSearch";
import Register from "./components/Register";
import Payment from "./components/PaymentPage";
import Supply from "./components/SupplyPage";
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
import MyStores from "./components/MyStores";
import BuyCart from "./components/BuyCart";
import ManagerPermissions from "./components/ManagerPermissions";
import ViewBidsStatusNevigator from "./components/ViewBidsStatusNevigator";
import Notifications from "./components/Notifications";
import { useEffect } from 'react';
import {useState} from "react";
import ViewRules from "./components/ViewRules";
import AddPurchase from "./components/AddPurchase";
import CreatePredict from "./components/CreatePredict";
import StorePolicies from "./components/StorePolicies";
import AdminViewStorePurchaseHistory from "./components/AdminViewStorePurchaseHistory";
import AdminViewUserPurchaseHistory from "./components/AdminViewUserPurchaseHistory";





export default function App() {


  const [snackbar, setSnackbar] = useState(null);
  const handleCloseSnackbar = () => setSnackbar(null);
  const [user, setUser] = useState(User.guest());
  useEffect(()=>{get_online_user()}, []);

  const connectApi = new ConnectApi();
  const get_online_user = async () => {
      let response = await connectApi.get_online_user();
      console.log(response.value);
      if(!response.was_exception)
      {
        setUser(response.value);
        setSnackbar({ children: response.message, severity: 'success' });

      }
      else
      {
        setSnackbar({ children: response.message, severity: 'error' });

      }
      
  }
  const updateUserState = (user ) => {
    console.log("in updateUserState\n\n\n\n\n");
    console.log(user);
    setUser(user);
  }
  const get_state=() => {
    return user;
  }


  return (
      <>
          <BrowserRouter>
        <NavBar
          updateUserState={updateUserState}
          user = {user}
          
        ></NavBar>


          {/* --------------------------------------From Home Page---------------------------------- */}

        <Routes>
          <Route
            path="/"
            element={
              <HomePage
                user={user}
                updateUserState={updateUserState}
              />
            }
          ></Route>
          <Route
            path="/Login"
            element={
              <Login
                updateUserState={updateUserState}
                user={user}
              />
            }
          ></Route>
          <Route
            path="/Register"
            element={
              <Register updateUserState={updateUserState} />
            }
          ></Route>
          <Route path="/ShoppingCart" element={<ShoppingCart />}></Route>
          <Route path="/UserViewQuestions" element={<UserViewQuestions />}></Route>

          {/* --------------------------------------Store Page---------------------------------- */}


          <Route path="/StorePage/:id" element={<StorePageNevigator />}></Route>
          <Route path="MyStores/StorePage/:id" element={<StorePageNevigator />}></Route>
          <Route path="/AllStores/StorePage/:id" element={<StorePageNevigator />}></Route>
          <Route
            path="/AdminSendMessage"
            element={<AdminSendMessage />}
          ></Route>
          <Route path="/AdminPage" element={<AdminPage />}></Route>

          {/* -------------------------------------- Admin Page---------------------------------- */}

          <Route
            path="/AdminPage/AdminViewStorePurchaseHistory/:id"
            element={<AdminViewStorePurchaseHistory />}
          ></Route>
          <Route
            path="/AdminPage/AdminViewUserPurchaseHistory/:user_email"
            element={<AdminViewUserPurchaseHistory />}
          ></Route>
          
          {/* -------------------------------------- Store Managment---------------------------------- */}
          <Route
            path="/StorePage/:id/StoreManagment"
            element={<StoreManagmentNevigator />}
          ></Route>
           <Route
            path="/MyStores/StorePage/:id/StoreManagment"
            element={<StoreManagmentNevigator />}
          ></Route>
          <Route
            path="/AllStores/StorePage/:id/StoreManagment"
            element={<StoreManagmentNevigator />}
          ></Route>
          
         
        
         {/* -------------------------------------- Store Policies---------------------------------- */}
          <Route
            path="/StorePage/:id/StoreManagment/StorePolicies"
            element={<StorePolicies/>} 
          ></Route>
            <Route
            path="/AllStores/StorePage/:id/StoreManagment/StorePolicies"
            element={<StorePolicies/>} 
          ></Route>
            <Route
            path="/MyStores/StorePage/:id/StoreManagment/StorePolicies"
            element={<StorePolicies/>} 
          ></Route>

 {/* -------------------------------------- Manager View Store Questions---------------------------------- */}
          <Route
            path="/StorePage/:id/StoreManagment/ManagerViewStoreQuestions"
            element={<MangerViewStoreQuestionsNevigator />}
          ></Route>
           <Route
            path="MyStores/StorePage/:id/StoreManagment/ManagerViewStoreQuestions"
            element={<MangerViewStoreQuestionsNevigator />}
          ></Route>
           <Route
            path="AllStores/StorePage/:id/StoreManagment/ManagerViewStoreQuestions"
            element={<MangerViewStoreQuestionsNevigator />}
          ></Route>
    {/* -------------------------------------- View Staff Information---------------------------------- */}

          <Route
            path="/StorePage/:id/StoreManagment/ViewStaffInformation"
            element={<ViewStaffInformationNevigator />}
          ></Route>
          <Route
            path="MyStores/StorePage/:id/StoreManagment/ViewStaffInformation"
            element={<ViewStaffInformationNevigator />}
          ></Route>
          <Route
            path="AllStores/StorePage/:id/StoreManagment/ViewStaffInformation"
            element={<ViewStaffInformationNevigator />}
          ></Route>

 {/* -------------------------------------- Manager Permissions---------------------------------- */}
          <Route
            path="/StorePage/:id/StoreManagment/ManagerPermissions/:user_email"
            element={<ManagerPermissions />}
          ></Route>
          <Route
            path="MyStores/StorePage/:id/StoreManagment/ManagerPermissions/:user_email"
            element={<ManagerPermissions />}
          ></Route>
          <Route
            path="AllStores/StorePage/:id/StoreManagment/ManagerPermissions/:user_email"
            element={<ManagerPermissions />}
          ></Route>
 {/* -------------------------------------- View Store Purchase History---------------------------------- */}


          <Route
            path="/StorePage/:id/StoreManagment/ViewStorePurchaseHistory"
            element={<ViewStorePurchaseHistoryNevigator />}
          ></Route>
          <Route
            path="MyStores/StorePage/:id/StoreManagment/ViewStorePurchaseHistory"
            element={<ViewStorePurchaseHistoryNevigator />}
          ></Route>
          <Route
            path="AllStores/StorePage/:id/StoreManagment/ViewStorePurchaseHistory"
            element={<ViewStorePurchaseHistoryNevigator />}
          ></Route>

 {/* -------------------------------------- End --- Policies---------------------------------- */}

          <Route
            path="/EditProfile"
            element={<EditProfile get_state={get_state} />}
          ></Route>
          <Route
            path="/EditProfilePremium"
            element={
              <EditProfilePremium get_state={get_state} />
            }
          ></Route>
          <Route
            path="/StorePage/:store_id/ProductPage/:product_id"
            element={<ProductPageNevigator />}
          ></Route>
          <Route
            path="/StorePage/:id/ViewBidsStatus"
            element={<ViewBidsStatusNevigator />}
          ></Route>
          <Route
            path="/AllStores"
            element={<AllStores/>}
          ></Route>
          <Route
            path="/MyStores"
            element={<MyStores/>}
          ></Route>

          <Route path="/ProductPage" element={<ProductPage />}></Route>
          {/* <Route exact path="/home/:amit" element={<ProductPage product_id={1} store_id={1}/>}></Route> */}
          <Route path="/ViewStat" element={<ViewStat user={user} />}></Route>
          <Route
            path="/AdminViewUserQuestions"
            element={<AdminViewUserQuestions />}
          ></Route>
         
          <Route path="/ShoppingCart/BuyCart" element={<BuyCart/>}></Route>
          {/* <Route path="/Payment" element={<PaymentPage/>}></Route>
          <Route path="/Supply" element={<SupplyPage/>}></Route> */}
          <Route
            path="/ViewUserPurchaseHistory"
            element={<ViewUserPurchaseHistory />}
          ></Route>
          

          <Route path="/Notifications" element={<Notifications />}></Route>
          
          {/* <Route path="/UserPurchaseHistory" element={<UserPurchaseHistory />}></Route> */}
          {/* <Route path="/UserQuestions" element={<?? />}></Route>
          // notificans
          <Route path="/Notifications" element={<?? />}></Route> } */}
        </Routes>

        
      </BrowserRouter>
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
};

// /////////////////////////////////////////////////////////////////////////
// export default class App extends Component {
//   static displayName = App.name;
//   constructor(props) {
//     super(props);
//     console.log("in constructor of APP");
//     this.state = {
//       user:User.guest(),
//       messages: [],
//       snackbar: null,
//     };
//     // if (this.state.user === undefined)
//     // {
//     //   console.log("ppp");
//     //   this.setState({
//     //     // Important: read `state` instead of `this.state` when updating.
//     //     user: User.guest(),
       
//     //   });
//     // }
//     this.connectApi = new ConnectApi();
//     this.updateUserState = this.updateUserState.bind(this);
//     this.get_online_user();
//     // const user = React.createContext('user');
      
//   }
//   async componentDidMount() {
//     this.get_online_user();
//     // if (this.state.user == undefined)
//     // {
//     //   console.log("ppp");
//     //   this.setState({
//     //     // Important: read `state` instead of `this.state` when updating.
//     //     user: User.guest(),
       
//     //   });
//     // }
//     console.log("in component did mount - home page");
//   }

  //  updateUserState(user) {
  //   console.log("in updateUserState\n\n\n\n\n");
  //   console.log(user);
  //   console.log(this.state.user);
  //   this.setState({
  //     // Important: read `state` instead of `this.state` when updating.
  //     user: user,
     
  //   });
    

//     console.log(this.state.user);
//   }
//   async add_store_to_user(store_id)
//   {
//     this.state.user.add_store(store_id);
//     console.log("in add store to user - store id -> "+store_id);

//   }


//   // async updateUserStateName(name) {
//   //   console.log("in set UserState - name\n\n\n\n\n");
//   //   this.setState({
//   //     user.name: name,
//   //     })
//   //   }

  // get_state() {
  //   return this.state.user;
  // }

//   async get_online_user(){
//       let response = await this.connectApi.get_online_user()
//       this.setState({user : response.value})
//   }
  

//   render() {
//     return (
//       <BrowserRouter>
//         <NavBar
//           updateUserState={this.updateUserState.bind(this)}
//           user = {this.state.user}
          
//         ></NavBar>

//         <Routes>
//           <Route
//             path="/"
//             element={
//               <HomePage
//                 user={this.state.user}
//                 updateUserState={this.updateUserState.bind(this)}
//                 add_store_to_user = {this.add_store_to_user.bind(this)}
//               />
//             }
//           ></Route>
//           <Route
//             path="/Login"
//             element={
//               <Login
//                 updateUserState={this.updateUserState.bind(this)}
//                 user={this.state.user}
//               />
//             }
//           ></Route>
//           <Route
//             path="/Register"
//             element={
//               <Register updateUserState={this.updateUserState.bind(this)} />
//             }
//           ></Route>
//           <Route path="/HomePageSearch" element={<HomePageSearch />}></Route>
//           {/* <Route path="/StorePage" element={<StorePageNevigator/>}></Route> */}
//           <Route path="/StorePage/:id" element={<StorePageNevigator />}></Route>
//           <Route path="MyStores/StorePage/:id" element={<StorePageNevigator />}></Route>
//           <Route path="/AllStores/StorePage/:id" element={<StorePageNevigator />}></Route>
//           <Route
//             path="/AdminSendMessage"
//             element={<AdminSendMessage />}
//           ></Route>
//           <Route path="/AdminPage" element={<AdminPage />}></Route>
          // <Route path="/ShoppingCart" element={<ShoppingCart />}></Route>
//           <Route
//             path="/StorePage/:id/StoreManagment"
//             element={<StoreManagmentNevigator />}
//           ></Route>
//           <Route
//             path="/StorePage/:id/StoreManagment/AddDiscount"
//             element={<AddDiscount/>} //have to pass store id
//           ></Route>
//           <Route
//             path="/EditProfile"
//             element={<EditProfile get_state={this.get_state.bind(this)} />}
//           ></Route>
//           <Route
//             path="/EditProfilePremium"
//             element={
//               <EditProfilePremium get_state={this.get_state.bind(this)} />
//             }
//           ></Route>
//           <Route
//             path="/StorePage/:store_id/ProductPage/:product_id"
//             element={<ProductPageNevigator />}
//           ></Route>
//           <Route
//             path="/StorePage/:id/ViewBidsStatus"
//             element={<ViewBidsStatusNevigator />}
//           ></Route>
//           <Route
//             path="/AllStores"
//             element={<AllStores/>}
//           ></Route>
//           <Route
//             path="/MyStores"
//             element={<MyStores/>}
//           ></Route>

//           <Route path="/ProductPage" element={<ProductPage />}></Route>
//           {/* <Route exact path="/home/:amit" element={<ProductPage product_id={1} store_id={1}/>}></Route> */}
//           <Route path="/ViewStat" element={<ViewStat user={this.state.user} />}></Route>
//           <Route
//             path="/AdminViewUserQuestions"
//             element={<AdminViewUserQuestions />}
//           ></Route>
//           <Route
//             path="/StorePage/:id/StoreManagment/ManagerViewStoreQuestions"
//             element={<MangerViewStoreQuestionsNevigator />}
//           ></Route>
//           {/* <Route path="/StoreManagment/ManagerViewStoreQuestions/:id" element={<ManagerViewStoreQuestions />}></Route> */}
//           {/* <Route path="/UserViewQuestions" element={<UserViewQuestions />}></Route> */}
//           <Route
//             path="/StorePage/:id/StoreManagment/ViewStaffInformation"
//             element={<ViewStaffInformationNevigator />}
//           ></Route>
//           <Route path="/ShoppingCart/BuyCart" element={<BuyCart/>}></Route>
//           {/* <Route path="/Payment" element={<PaymentPage/>}></Route>
//           <Route path="/Supply" element={<SupplyPage/>}></Route> */}
//           <Route
//             path="/ViewUserPurchaseHistory"
//             element={<ViewUserPurchaseHistory />}
//           ></Route>
//           <Route
//             path="/StorePage/:id/StoreManagment/ManagerPermissions/:user_email"
//             element={<ManagerPermissions />}
//           ></Route>

//           <Route path="/Notifications" element={<Notifications />}></Route>

//           <Route
//             path="/StorePage/:id/StoreManagment/ViewStorePurchaseHistory"
//             element={<ViewStorePurchaseHistoryNevigator />}
//           ></Route>

//           {/* <Route path="/UserPurchaseHistory" element={<UserPurchaseHistory />}></Route> */}
//           {/* <Route path="/UserQuestions" element={<?? />}></Route>
//           // notificans
//           <Route path="/Notifications" element={<?? />}></Route> } */}
//         </Routes>

//         {!!this.state.snackbar && (
//           <Snackbar
//             open
//             anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
//             onClose={this.handleCloseSnackbar}
//             autoHideDuration={6000}
//           >
//             <Alert
//               {...this.state.snackbar}
//               onClose={this.handleCloseSnackbar}
//             />
//           </Snackbar>
//         )}
//       </BrowserRouter>


//     );
//   }
// }
