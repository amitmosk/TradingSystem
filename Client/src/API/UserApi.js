// import axios from "axios";
import {CONNECTION_ERROR, CATCH, VIEW_USER_QUESTIONS,  VIEW_USER_PURCHASE_HISTORY, GET_USER_EMAIL, GET_USER_NAME, GET_USER_LAST_NAME, EDIT_PASSWORD, EDIT_NAME , EDIT_LAST_NAME, UNREGISTER, EDIT_NAME_PREMIUM, EDIT_LAST_NAME_PREMIUM, EDIT_PASSWORD_PREMIUM, GET_USER_SECURITY_QUESTION, IMPROVE_SECURITY} from "./ApiPaths";
import { Response } from "./Response";
// const instance = axios.create(
//     {withCredentials : true}
// );
const instance = require('axios');


export class UserApi {

    view_user_purchase_history() {
        return instance.get(VIEW_USER_PURCHASE_HISTORY,
            {
                params:{session_id:JSON.parse(sessionStorage.getItem("session_id"))}
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
    get_user_email() {
        return instance.get(GET_USER_EMAIL,{
            params:{session_id:JSON.parse(sessionStorage.getItem("session_id"))}
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
    get_user_name() {
        return instance.get(GET_USER_NAME,{
            params:{session_id:JSON.parse(sessionStorage.getItem("session_id"))}
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
    get_user_last_name() {
        return instance.get(GET_USER_LAST_NAME,{
            params:{session_id:JSON.parse(sessionStorage.getItem("session_id"))}
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
    edit_password(old_password, password) {
        return instance.get(EDIT_PASSWORD,
            {
                params:{ old_pw : old_password,
                    password : password,session_id:JSON.parse(sessionStorage.getItem("session_id"))}
               
            })
            .then(res => {
                return new Response(res.data)
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
    edit_name(new_name) {
        console.log("edit name welcome user api");
        return instance.get(EDIT_NAME,
            {
                params:{
                    new_name : new_name,session_id:JSON.parse(sessionStorage.getItem("session_id"))}
                
            })
            .then(res => {
                console.log("edit name user API thennn");
                return new Response(res.data)
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

    edit_last_name(new_last_name) {
        return instance.get(EDIT_LAST_NAME,
            {
                params:{new_last_name : new_last_name,session_id:JSON.parse(sessionStorage.getItem("session_id"))}
                
            })
            .then(res => {
                return new Response(res.data)
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

    unregister(password) {
        return instance.get(UNREGISTER,
            {
                params:{password : password,session_id:JSON.parse(sessionStorage.getItem("session_id"))}
                
            })
            .then(res => {
                return new Response(res.data)
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
    edit_name_premium(new_name, answer) {
        return instance.get(EDIT_NAME_PREMIUM,
            {
                params:{
                    new_name : new_name,
                    answer : answer,session_id:JSON.parse(sessionStorage.getItem("session_id"))
                                    }
                

            })
            .then(res => {
                return new Response(res.data)
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
    edit_last_name_premium(new_last_name, answer) {
        return instance.get(EDIT_LAST_NAME_PREMIUM,
            {
                params:{
                    new_last_name : new_last_name,
                    answer : answer,session_id:JSON.parse(sessionStorage.getItem("session_id"))
                                    }
                
            })
            .then(res => {
                return new Response(res.data)
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
    edit_password_premium(old_password, new_password, answer) {
        return instance.get(EDIT_PASSWORD_PREMIUM,
            {
                params:{old_password : old_password,
                    new_password : new_password,
                    answer : answer,session_id:JSON.parse(sessionStorage.getItem("session_id"))
                                    }
                
            })
            .then(res => {
                return new Response(res.data)
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
    get_user_security_question() {
        return instance.get(GET_USER_SECURITY_QUESTION,{ params:{session_id:JSON.parse(sessionStorage.getItem("session_id"))}})
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
    get_user_questions() {
        return instance.get(VIEW_USER_QUESTIONS,{ params:{session_id:JSON.parse(sessionStorage.getItem("session_id"))}})
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

    improve_security(password, question, answer) {
        return instance.get(IMPROVE_SECURITY,
            {
                params:{password : password,
                    question : question,
                    premAnswer : answer,session_id:JSON.parse(sessionStorage.getItem("session_id"))
                                    }
                
            })
            .then(res => {
                return Response.create(null, res.data.was_exception, res.data.message);
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