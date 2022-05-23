// import axios from "axios";
import {CONNECTION_ERROR, CATCH, EMPLOYEE_BASE_REST_API_URL, LOGIN_PATH, REGISTER_PATH,LOGOUT_PATH} from "./ApiPaths";
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
                console.log(res.data)
                const user = new User(res.data.value);
                return Response.create(user,res.data.was_exception,res.data.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    logout() {
        return instance.get(LOGOUT_PATH)
            .then(res => {
                const user_guest = User.guest()
                return Response.create(user_guest,res.data.was_exception,res.data.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
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
                return Response.create(user,res.data.was_exception,res.data.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
}