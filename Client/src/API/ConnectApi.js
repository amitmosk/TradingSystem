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
    
    login(email, password) {
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

    logout() {
        return instance.get(LOGOUT_PATH)
            .then(res => {
                
                return new Response(res.data);
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
                // let response = res.data;
                // let user = new User
                console.log(res.data.value);
                console.log(res.data.was_exception);
                console.log(res.data.message);
                return("res === "+res+"\n\n");
                return Response(res.data);
            })
            .catch(res => console.log("fuck!!!\n\n\n\n\n"));
    }
}