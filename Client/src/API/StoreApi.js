// import axios from "axios";
import {CONNECTION_ERROR, CATCH, FIND_STORE_INFORMATION, OPEN_STORE, RATE_STORE, SEND_QUESTION_TO_STORE,
     ADD_PRODUCT_TO_STORE, DELETE_PRODUCT_FROM_CART, SET_STORE_PURCHASE_POLICY,
      SET_STORE_DISCOUNT_POLICY, SET_STORE_PURCHASE_RULES, ADD_OWNER, DELETE_OWNER, 
      ADD_MANAGER, DELETE_MANAGER, CLOSE_STORE_TEMPORARILY, OPEN_CLOSE_STORE,
      VIEW_STORE_MANAGEMENT_INFORMATION, MANAGER_ANSWER_QUESTION, VIEW_STORE_PURCHASES_HISTORY, 
      MANAGER_VIEW_STORE_QUESTIONS, EDIT_MANAGER_PERMISSIONS, GET_PRODUCTS_BY_STORE_ID,GET_ALL_STORES, DELETE_PRODUCT_FROM_STORE} from "./ApiPaths";
import { Response } from "./Response";
import { Store } from "../ServiceObjects/Store";
import { Product } from "../ServiceObjects/Product";
// const instance = axios.create(
//     {withCredentials : true}
// );
const instance = require('axios');

export class StoreApi {
    find_store_information(store_id) {
        console.log("in find store information -> dtore id = "+store_id);
        return instance.get(FIND_STORE_INFORMATION,
            {
                params:{ store_id : store_id,}
            })
            .then(res => {
                let response = res.data;
                let store_info = new Store(response.value);
                return Response.create(store_info, false, response.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    open_store(store_name)    {
        return instance.get(OPEN_STORE,
            {
                params:{store_name : store_name,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    
    rate_store(store_id, rate) {
        return instance.get(RATE_STORE,
            {
                params:{store_id: store_id,
                    rate : rate,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    send_question_to_store(store_id, question) {
        return instance.get(SEND_QUESTION_TO_STORE,
            {
                params:{store_id: store_id,
                    question : question,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    
    add_product_to_store(store_id, quantity,name, price, category, key_words) {
        return instance.get(ADD_PRODUCT_TO_STORE,
            {
                params:{store_id : store_id,
                    quantity : quantity,
                    name : name,
                    price : price,
                    category : category,
                    key_words : key_words,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    delete_product_from_store(product_id, store_id) {
        return instance.get(DELETE_PRODUCT_FROM_STORE,
            {
                params:{product_id : product_id,
                    store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    set_store_purchase_policy(store_id, policy) {
        return instance.get(SET_STORE_PURCHASE_POLICY,
            {
                params:{store_id : store_id,
                    policy : policy,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    set_store_discount_policy(store_id, policy) {
        return instance.get(SET_STORE_DISCOUNT_POLICY,
            {
                params:{store_id : store_id,
                    policy : policy,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    set_store_purchase_rules(store_id, rule) {
        return instance.get(SET_STORE_PURCHASE_RULES,
            {
                params:{store_id : store_id,
                    rule : rule,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    add_owner(user_email_to_appoint, store_id)  {
        return instance.get(ADD_OWNER,
            {
                params:{user_email_to_appoint : user_email_to_appoint,
                    store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
   
    delete_owner(user_email_to_delete_appointment, store_id)  {
        return instance.get(DELETE_OWNER,
            {
                params:{user_email_to_delete_appointment : user_email_to_delete_appointment,
                    store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

       
    add_manager(user_email_to_appoint, store_id)  {
        return instance.get(ADD_MANAGER,
            {
                params:{user_email_to_appoint : user_email_to_appoint,
                    store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }


    delete_manager(user_email_to_delete_appointment, store_id)  {
            return instance.get(DELETE_MANAGER,
                {
                    params:{user_email_to_delete_appointment : user_email_to_delete_appointment,
                        store_id : store_id,}
                    
                    
                })
                .then(res => {
                    return new Response(res.data)
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        }
    close_store_temporarily(store_id){
        return instance.get(CLOSE_STORE_TEMPORARILY,
            {
                params:{store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    open_close_store(store_id){
        return instance.get(OPEN_CLOSE_STORE,
            {
                params:{store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    view_store_management_information(store_id){
        return instance.get(VIEW_STORE_MANAGEMENT_INFORMATION,
            {
                params:{store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data) //value is string answer
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    manager_view_store_questions(store_id){ // value is list of strings
        return instance.get(MANAGER_VIEW_STORE_QUESTIONS,
            {
                params:{store_id : store_id,}

            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    manager_answer_question(store_id, question_id, answer){
        return instance.get(MANAGER_ANSWER_QUESTION,
            {
                params:{store_id : store_id,
                    question_id : question_id,
                    answer : answer,}
                

            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    view_store_purchases_history(store_id){ // value is string of the purchases history
        return instance.get(VIEW_STORE_PURCHASES_HISTORY,
            {
                params:{store_id : store_id,}
                

            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    edit_manager_permissions(manager_email, store_id, permissions){
        return instance.get(EDIT_MANAGER_PERMISSIONS,
            {
                params:{manager_email : manager_email,
                    store_id : store_id,
                    permissions : permissions,}
                

            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    get_products_by_store_id(store_id){
        return instance.get(GET_PRODUCTS_BY_STORE_ID,
            {
                params:{store_id : store_id}
            })
            .then(res => {
                let response = res.data;
                //traverse the products and create product for each element on the list
                //create response with the list of products
                const arr = [];
                res.data.value.map(p => arr.push(new Product(p)));
                return Response.create(arr,res.data.wasException,res.data.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    get_all_stores(){
        return instance.get(GET_ALL_STORES,
            {
            })
            .then(res => {
                let response = res.data;
                //traverse the products and create product for each element on the list
                //create response with the list of products
                const arr = [];
                response.value.map(s => arr.push(new Store(s)));
                return Response.create(arr,response.wasException,response.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    

    
}

