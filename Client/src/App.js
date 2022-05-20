import { Component } from 'react';
import './App.css';
import {BrowserRouter, Link, Route, useHistory} from "react-router-dom";
import Login from './components/Login';
import HomePageSearch from './components/HomePageSearch';
import Register from './components/Register';
import Payment from './components/Payment';
import Supply from './components/Supply';
// import * as React from 'react';
// import ReactDOM from 'react-dom';
import Button from '@mui/material/Button';
import StorePage from './components/StorePage';
import { ProductPage } from './components/ProductPage';
import { Product } from './ServiceObjects/Product';

// function App() {
//   return <Button variant="contained">Hello World</Button>;
// }

export default class App extends Component {
  // return (
    // <div>     
    //   <ListEmployee />
    // </div>
  // );



  render () {
    let prod = Product.create("tom123",1,1,"category","","");
    // let prod = "prod"
    return (
          <BrowserRouter>
          <Layout>
          <Route exact path='/' component={Login} />
            <Route exact path='/register' component={Register}/>
            </Layout>
          </BrowserRouter>
    );
    }
  }



// const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

// const axios = require('axios');
// axios.get(EMPLOYEE_BASE_REST_API_URL).then(response => {console.log(response.data)}).catch(error => {
//   console.log("lllllll")
// });

// export default App;