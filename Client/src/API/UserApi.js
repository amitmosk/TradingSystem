// import axios from "axios";
import {CONNECTION_ERROR, CATCH, VIEW_USER_QUESTIONS,  VIEW_USER_PURCHASE_HISTORY, GET_USER_EMAIL, GET_USER_NAME, GET_USER_LAST_NAME, EDIT_PASSWORD, EDIT_NAME , EDIT_LAST_NAME, UNREGISTER, EDIT_NAME_PREMIUM, EDIT_LAST_NAME_PREMIUM, EDIT_PASSWORD_PREMIUM, GET_USER_SECURITY_QUESTION, IMPROVE_SECURITY} from "./ApiPaths";
import { Response } from "./Response";
// const instance = axios.create(
//     {withCredentials : true}
// );
const instance = require('axios');


export class UserApi {

    view_user_purchase_history() {
        return instance.get(VIEW_USER_PURCHASE_HISTORY)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    get_user_email() {
        return instance.get(GET_USER_EMAIL)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    get_user_name() {
        return instance.get(GET_USER_NAME)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    get_user_last_name() {
        return instance.get(GET_USER_LAST_NAME)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    edit_password(old_password, password) {
        return instance.get(EDIT_PASSWORD,
            {
                params:{ old_pw : old_password,
                    password : password,}
               
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    edit_name(new_name) {
        console.log("edit name welcome user api");
        return instance.get(EDIT_NAME,
            {
                params:{
                    new_name : new_name,}
                
            })
            .then(res => {
                console.log("edit name user API thennn");
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    edit_last_name(new_last_name) {
        return instance.get(EDIT_LAST_NAME,
            {
                params:{new_last_name : new_last_name,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    unregister(password) {
        return instance.get(UNREGISTER,
            {
                params:{password : password,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    edit_name_premium(new_name, answer) {
        return instance.get(EDIT_NAME_PREMIUM,
            {
                params:{
                    new_name : new_name,
                    answer : answer,
                                    }
                

            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    edit_last_name_premium(new_last_name, answer) {
        return instance.get(EDIT_LAST_NAME_PREMIUM,
            {
                params:{
                    new_last_name : new_last_name,
                    answer : answer,
                                    }
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    edit_password_premium(old_password, new_password, answer) {
        return instance.get(EDIT_PASSWORD_PREMIUM,
            {
                params:{old_password : old_password,
                    new_password : new_password,
                    answer : answer,
                                    }
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    get_user_security_question() {
        return instance.get(GET_USER_SECURITY_QUESTION)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    get_user_questions() {
        return instance.get(VIEW_USER_QUESTIONS)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    improve_security(password, question, answer) {
        return instance.get(IMPROVE_SECURITY,
            {
                params:{password : password,
                    question : question,
                    premAnswer : answer,
                                    }
                
            })
            .then(res => {
                return Response.create(null,res.data.was_exception,res.data.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    


    

    
    
   
}