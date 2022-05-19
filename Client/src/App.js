import { Component } from 'react';
import './App.css';
import Login from './components/Login';
import HomePageSearch from './components/HomePageSearch';
import Register from './components/Register';
import Payment from './components/Payment';
import Supply from './components/Supply';
// import * as React from 'react';
// import ReactDOM from 'react-dom';
import Button from '@mui/material/Button';
import StorePage from './components/StorePage';

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
    return (
            <div> 
            {/* <HomePageSearch /> 
            <Login/>
            <Payment/>
            <Supply/>
            <Register/>   
            <Login /> */}

            <StorePage/>
          </div>
    );
  }
}


// const EMPLOYEE_BASE_REST_API_URL = "http://localhost:8080/amit";

// const axios = require('axios');
// axios.get(EMPLOYEE_BASE_REST_API_URL).then(response => {console.log(response.data)}).catch(error => {
//   console.log("lllllll")
// });

//export default App;