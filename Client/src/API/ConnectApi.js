import {CONNECTION_ERROR, CATCH,ONLINE_USER_PATH, LOGIN_PATH, REGISTER_PATH,LOGOUT_PATH, NOTIFICATIONS_PATH} from "./ApiPaths";
import { Response } from "./Response";
import { User } from "../ServiceObjects/User";

const instance = require('axios');

export class ConnectApi {

    
    login(email, password) {
        return instance.get(LOGIN_PATH,
            {
                params:{ email: email,
                    password: password,}
                
            })
            .then(res => {
                console.log("user = " +res.data.value.storesManaged+"\n\n\n\n");
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
                console.log("user = " +res.data.value.storesManaged+"\n\n\n\n");
                const user = new User(res.data.value);
                return Response.create(user,res.data.was_exception,res.data.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    get_online_user() {
        return instance.get(ONLINE_USER_PATH)
            .then(res => {
                const user_guest = new User(res.data.value);
                return Response.create(user_guest,res.data.was_exception,res.data.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    get_notifications(email) {
        return instance.get(NOTIFICATIONS_PATH,
            {
                params:{ email: email,
                    }
                
            })
            .then(res => {
                console.log(res.data)
                const user = new User(res.data.value);
                return Response.create(user,res.data.was_exception,res.data.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
}