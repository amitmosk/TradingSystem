// import axios from "axios";
import {EMPLOYEE_BASE_REST_API_URL, LOGIN_PATH, REGISTER_PATH,LOGOUT_PATH} from "./ApiPaths";
import { Response } from "./Response";
// const instance = axios.create(
//     {withCredentials : true}
// );
// const axioss = require('axios');
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
    
    Login(email, password) {
        return instance.get(LOGIN_PATH,
            {
                params:{ email: email,
                    password: password,}
                
            })
            .then(res => {
                const websocket = require('ws');
                var ws = new websocket(WEBSOCKETURL);
                // ws.onopen(TODO AMIT)
                return new Response(res.data);
            })
            .catch(res => undefined);
    }

    Logout() {
        return instance.get(LOGOUT_PATH)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => undefined);
    }
    
    Register(email, password, first_name, last_name) {
        return instance.get(REGISTER_PATH,
            {
                params:{email: email,
                    password: password,
                    first_name: first_name,
                    last_name: last_name,}
                
            })
            .then(res => {
                return Response(res.data);
            })
            .catch(res => undefined);
    }
}