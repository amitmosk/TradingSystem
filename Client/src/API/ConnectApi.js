import {
    CONNECTION_ERROR, CATCH, ONLINE_USER_PATH,
    LOGIN_PATH, REGISTER_PATH, LOGOUT_PATH,
    NOTIFICATIONS_PATH, SAVE_NOTIFICATIONS, GET_NOTIFICATIONS_LIST
} from "./ApiPaths";
import { Response } from "./Response";
import { User } from "../ServiceObjects/User";

const instance = require('axios');
// const sessionStorage.getItem("session_id")=sessionStorage.getItem("session_id");
export class ConnectApi {


    login(email, password) {
        console.log("login"+sessionStorage.getItem("session_id"));
        return instance.get(LOGIN_PATH,
            {
                params:{ email: email,
                    password: password,
                    session_id:sessionStorage.getItem("session_id")}
            })
            .then(res => {
                console.log("user = " + res.data.value.storesManaged + "\n\n\n\n");
                const user = new User(res.data.value);
                return Response.create(user, res.data.was_exception, res.data.message);
            })
            .catch(function (res) {
                if (res.message == "Network Error") {
                    console.log(res.message)
                    return Response.create(CATCH, true, CONNECTION_ERROR)

                }
                else {
                    console.log(res.message)
                    return Response.create(CATCH, true, "Wrong email or password")
                }
            });
    }

    logout() {
        return instance.get(LOGOUT_PATH,{params:{
            session_id:JSON.parse(sessionStorage.getItem("session_id"))
         }})
            .then(res => {
                const user_guest = User.guest()
                return Response.create(user_guest, res.data.was_exception, res.data.message);
            })
            .catch(function (res) {
                if (res.message == "Network Error") {
                    console.log(res.message)
                    return Response.create(CATCH, true, CONNECTION_ERROR)

                }
                else {
                    console.log(res.message)
                    return Response.create(CATCH, true, res.message)
                }
            });
    }

    register(email, password, first_name, last_name, birthdate) {
        console.log("register"+sessionStorage.getItem("session_id"));
        return instance.get(REGISTER_PATH,
            {
                params: {
                    email: email,
                    pw: password,
                    name: first_name,
                    lastName: last_name,
                    birth_date : birthdate,
                session_id:sessionStorage.getItem("session_id")}
            })
            .then(res => {
                console.log("user = " + res.data.value.storesManaged + "\n\n\n\n");
                const user = new User(res.data.value);
                return Response.create(user, res.data.was_exception, res.data.message);
            })
            .catch(res => Response.create(CATCH, true, CONNECTION_ERROR));
    }
    get_online_user() {
        return instance.get(ONLINE_USER_PATH,{
            params:{session_id:sessionStorage.getItem("session_id")}
        })
            .then(res => {
                const user_guest = new User(res.data.value);
                return Response.create(user_guest, res.data.was_exception, res.data.message);
            })
            .catch(function (res) {
                if (res.message == "Network Error") {
                    console.log(res.message)
                    return Response.create(CATCH, true, CONNECTION_ERROR)

                }
                else {
                    console.log(res.message)
                    return Response.create(CATCH, true, res.message)
                }
            });
    }
    get_notifications(email) {
        return instance.get(NOTIFICATIONS_PATH,
            {
                params:{ email: email,session_id:sessionStorage.getItem("session_id")
                    }
                
            })
            .then(res => {
                console.log(res.data)
                return new Response(res.data);
            })
            .catch(function (res) {
                if (res.message == "Network Error") {
                    console.log(res.message)
                    return Response.create(CATCH, true, CONNECTION_ERROR)

                }
                else {
                    console.log(res.message)
                    return Response.create(CATCH, true, res.message)
                }
            });
    }
    save_notifications(notification) {
        return instance.get(SAVE_NOTIFICATIONS,
            {
                params:{ notification: notification,session_id:sessionStorage.getItem("session_id")
                    }
                
            })
            .then(res => {
                console.log(res.data)
                return new Response(res.data);
            })
            .catch(function (res) {
                if (res.message == "Network Error") {
                    console.log(res.message)
                    return Response.create(CATCH, true, CONNECTION_ERROR)

                }
                else {
                    console.log(res.message)
                    return Response.create(CATCH, true, res.message)
                }
            });
    }
    get_notifications_list() {
        return instance.get(GET_NOTIFICATIONS_LIST,
            {
                params:{session_id:sessionStorage.getItem("session_id")
                    }
            })
            .then(res => {
                return new Response(res.data);
            })
            .catch(function (res) {
                if (res.message == "Network Error") {
                    console.log(res.message)
                    return Response.create(CATCH, true, CONNECTION_ERROR)

                }
                else {
                    console.log(res.message)
                    return Response.create(CATCH, true, res.message)
                }
            });
    }
}