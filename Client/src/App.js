import { Component } from 'react';
import './App.css';
import Login from './components/Login';
import HomePageSearch from './components/HomePageSearch';
import Register from './components/Register';
// import * as React from 'react';
// import ReactDOM from 'react-dom';
import Button from '@mui/material/Button';

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
            <HomePageSearch /> 
            <Login/>
            <Register/>   
            
            {/* <Login /> */}
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