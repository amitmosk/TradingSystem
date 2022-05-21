// import axios from "axios";
import {EMPLOYEE_BASE_REST_API_URL, LOGIN_PATH, REGISTER_PATH,LOGOUT_PATH} from "./ApiPaths";
import { Response } from "./Response";
import { User } from "../ServiceObjects/User";
// const instance = axios.create(
//     {withCredentials : true}
// );
const instance = require('axios');
const WEBSOCKETURL = "ws://localhost:8080/chat";
export class ConnectApi {

    //  amit (a) {
    //     console.log("pooo\n\n\n\n\n");
    //     return  axioss.post(EMPLOYEE_BASE_REST_API_URL)
    //         .then(res => {
    //             console.log("yessss\n\n\n\n\n");
    //             return new Response(res.data);
    //         })
    //         .catch(res => console.log("nooooooo\n\n\n\n\n"));

    // }
    
    login(email, password) {
        return instance.get(LOGIN_PATH,
            {
                params:{ email: email,
                    password: password,}
                
            })
            .then(res => {
                // const websocket = require('ws');
                // var ws = new websocket(WEBSOCKETURL);
                
                // ws.onopen = function(data) {ws.send("-email- want to open web socket with the server");};
                // ws.onmessage = function(data) {
                //     alert("new notification!");
                //     // update notifications UI with the new notification 
                //     console.log(data);
                //  }
                const user = new User(res.data.value);
                return Response.create(user,res.data.wasException,res.data.message);
            })
            .catch(res => undefined);
    }

    logout() {
        return instance.get(LOGOUT_PATH)
            .then(res => {
                return Response.create(null,res.data.wasException,res.data.message);
            })
            .catch(res => undefined);
    }
    
    register(email, password, first_name, last_name, birthdate) {
        return instance.get(REGISTER_PATH,
            {
                    params:{email: email,
                    pw: password,
                    name: first_name,
                    lastName: last_name,
                    birth_date : birthdate,}
            })
            .then(res => {
                const user = new User(res.data.value);
                return Response.create(user,res.data.wasException,res.data.message);
            }).catch(res => Response.create(null,true,"http failure"));
    }
}