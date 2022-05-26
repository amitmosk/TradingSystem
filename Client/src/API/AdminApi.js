// import axios from "axios";
import { sortAndDeduplicateDiagnostics } from "typescript";
import { CONNECTION_ERROR, CATCH,SEND_QUESTION_TO_ADMIN, CLOSE_STORE_PERMANENTLY, REMOVE_USER, ADMIN_VIEW_USERS_QUESTION ,
     ADMIN_ANSWER_USERS_QUESTION, ADMIN_VIEW_STORE_PURCHASES_HISTORY, ADMIN_VIEW_USER_PURCHASES_HISTORY, GET_MARKET_STATS} from "./ApiPaths";
import { Response } from "./Response";
import {Statistic} from "../ServiceObjects/statistic"
// const instance = axios.create(
//     {withCredentials : true}
// );
const instance = require('axios');


export class AdminApi {



    send_question_to_admin(question) {
        return instance.get(SEND_QUESTION_TO_ADMIN,
            {
                params:{question : question,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    close_store_permanently(store_id){
        return instance.get(CLOSE_STORE_PERMANENTLY,
            {
                params:{ store_id : store_id,}
               
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    remove_user(email){
        return instance.get(REMOVE_USER,
            {
                params:{email : email,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    admin_view_users_questions() {
        return instance.get(ADMIN_VIEW_USERS_QUESTION)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
   
    admin_answer_user_question(question_id, answer){
        return instance.get(ADMIN_ANSWER_USERS_QUESTION,
            {
                params:{question_id : question_id,
                    answer : answer,}
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    admin_view_store_purchases_history(store_id){
        return instance.get(ADMIN_VIEW_STORE_PURCHASES_HISTORY,
            {
                params:{store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    admin_view_user_purchases_history(user_email){
        return instance.get(ADMIN_VIEW_USER_PURCHASES_HISTORY,
            {
                params:{user_email : user_email,}
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    
    get_market_stats() {
        return instance.get(GET_MARKET_STATS)
            .then(res => {
                const stats = new Statistic(res.data.value);
                return Response.create(stats, res.data.was_exception, res.data.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
}