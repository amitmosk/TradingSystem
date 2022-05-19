import axios from "axios";
import { VIEW_USER_PURCHASE_HISTORY, GET_USER_EMAIL, GET_USER_NAME, GET_USER_LAST_NAME, EDIT_PASSWORD, EDIT_NAME , EDIT_LAST_NAME, UNREGISTER, EDIT_NAME_PREMIUM, EDIT_LAST_NAME_PREMIUM, EDIT_PASSWORD_PREMIUM, GET_USER_SECURITY_QUESTION, IMPROVE_SECURITY} from "./ApiPaths";
import { Response } from "./Response";
const instance = axios.create(
    {withCredentials : true}
);



export class UserApi {

    view_user_purchase_history() {
        return instance.get(VIEW_USER_PURCHASE_HISTORY)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => undefined);
    }
    get_user_email() {
        return instance.get(GET_USER_EMAIL)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => undefined);
    }
    get_user_name() {
        return instance.get(GET_USER_NAME)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => undefined);
    }
    get_user_last_name() {
        return instance.get(GET_USER_LAST_NAME)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => undefined);
    }
    edit_password(old_password, password) {
        return instance.post(EDIT_PASSWORD,
            {
                old_password : old_password,
                password : password,
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
    edit_name(password, new_name) {
        return instance.post(EDIT_NAME,
            {
                password : password,
                new_name : new_name,
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }

    edit_last_name(password, new_last_name) {
        return instance.post(EDIT_LAST_NAME,
            {
                password : password,
                new_last_name : new_last_name,
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }

    unregister(password) {
        return instance.post(UNREGISTER,
            {
                password : password,
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
    edit_name_premium(password, new_name, answer) {
        return instance.post(EDIT_NAME_PREMIUM,
            {
                password : password,
                new_name : new_name,
                answer : answer,


            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
    edit_last_name_premium(password, new_last_name, answer) {
        return instance.post(EDIT_LAST_NAME_PREMIUM,
            {
                password : password,
                new_last_name : new_last_name,
                answer : answer,
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
    edit_password_premium(old_password, new_password, answer) {
        return instance.post(EDIT_PASSWORD_PREMIUM,
            {
                old_password : old_password,
                new_password : new_password,
                answer : answer,
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
    get_user_security_question() {
        return instance.get(GET_USER_SECURITY_QUESTION)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => undefined);
    }

    improve_security(password, question, answer) {
        return instance.post(IMPROVE_SECURITY,
            {
                password : password,
                question : question,
                answer : answer,
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }

    


    

    
    
   
}