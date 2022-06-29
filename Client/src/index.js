import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import "bootstrap/dist/css/bootstrap.min.css";
//import 'semantic-ui-css/semantic.min.css';
import Button from '@mui/material/Button';
import { User } from './ServiceObjects/User';
import { ConnectApi } from './API/ConnectApi';
const connectApi = new ConnectApi();
// const get_online_user = async () => {
//   let response = await connectApi.get_online_user();
//   console.log("the online userrr");
//   console.log(response.value);
//   localStorage.setItem('user', response.value);
// }
const get_user_id = ()=> {
  let url = "http://localhost:8080/get_session_id";

  return fetch(url, {
    method: "GET",
    mode: "cors",
    headers: { "Content-Type": "application/json" },
  }).then(async (response) => {
    const data = await response.json();
    if (!response.ok) {
      return Promise.reject(data.error);
    }
    return Promise.resolve(data.value);
  });
}

const session_id = sessionStorage.getItem("session_id");
if (session_id === null) {
  get_user_id().then((userId) =>{
    console.log(userId);
    sessionStorage.setItem("session_id", userId);
    console.log(sessionStorage.getItem("session_id"));
});
}


const root = ReactDOM.createRoot(document.getElementById('root'));
if(sessionStorage.getItem("session_id") !== null)
{
  root.render(
    <React.StrictMode>
      <App/>
    </React.StrictMode>
  );

}
else{
  window.location.reload();
}


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
