// import axios from "axios";
import {  FIND_STORE_INFORMATION, OPEN_STORE, RATE_STORE, SEND_QUESTION_TO_STORE,
     ADD_PRODUCT_TO_STORE, DELETE_PRODUCT_FROM_CART, SET_STORE_PURCHASE_POLICY,
      SET_STORE_DISCOUNT_POLICY, SET_STORE_PURCHASE_RULES, ADD_OWNER, DELETE_OWNER, 
      ADD_MANAGER, DELETE_MANAGER, CLOSE_STORE_TEMPORARILY, OPEN_CLOSE_STORE,
      VIEW_STORE_MANAGEMENT_INFORMATION, MANAGER_ANSWER_QUESTION, VIEW_STORE_PURCHASES_HISTORY, MANAGER_VIEW_STORE_QUESTIONS, EDIT_MANAGER_PERMISSIONS} from "./ApiPaths";
import { Response } from "./Response";
import { StoreInformation } from "../ServiceObjects/Store";
// const instance = axios.create(
//     {withCredentials : true}
// );
const instance = require('axios');
const response_obj = new Response("","");



export class StoreApi {
    find_store_information(store_id) {
        return instance.get(FIND_STORE_INFORMATION,
            {
                params:{ store_id : store_id,}
                
            })
            .then(res => {
                let response = res.data;
                let store_info = new StoreInformation(response.value);
                return response_obj.create(store_info, response.message);
            })
            .catch(res => undefined);
    }
    open_store(store_name)    {
        return instance.get(OPEN_STORE,
            {
                params:{store_name : store_name,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
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
            .catch(res => undefined);
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
            .catch(res => undefined);
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
            .catch(res => undefined);
    }
    delete_product_from_store(product_id, store_id) {
        return instance.get(DELETE_PRODUCT_FROM_CART,
            {
                params:{product_id : product_id,
                    store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
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
            .catch(res => undefined);
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
            .catch(res => undefined);
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
            .catch(res => undefined);
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
            .catch(res => undefined);
    }
   
    delete_owner(user_email_to_appoint, store_id)  {
        return instance.get(DELETE_OWNER,
            {
                params:{user_email_to_appoint : user_email_to_appoint,
                    store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
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
            .catch(res => undefined);
    }


    delete_manager(user_email_to_appoint, store_id)  {
            return instance.get(DELETE_MANAGER,
                {
                    params:{user_email_to_appoint : user_email_to_appoint,
                        store_id : store_id,}
                    
                    
                })
                .then(res => {
                    return new Response(res.data)
                })
                .catch(res => undefined);
        }
    close_store_temporarily(store_id){
        return instance.get(CLOSE_STORE_TEMPORARILY,
            {
                params:{store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
    open_close_store(store_id){
        return instance.get(OPEN_CLOSE_STORE,
            {
                params:{store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
    view_store_management_information(store_id){
        return instance.get(VIEW_STORE_MANAGEMENT_INFORMATION,
            {
                params:{store_id : store_id,}
                
                
            })
            .then(res => {
                return new Response(res.data) //value is string answer
            })
            .catch(res => undefined);
    }
    manager_view_store_questions(store_id){ // value is list of strings
        return instance.get(MANAGER_VIEW_STORE_QUESTIONS,
            {
                params:{store_id : store_id,}

            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
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
            .catch(res => undefined);
    }
    view_store_purchases_history(store_id){ // value is string of the purchases history
        return instance.get(VIEW_STORE_PURCHASES_HISTORY,
            {
                params:{store_id : store_id,}
                

            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
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
            .catch(res => undefined);
    }
    

    
}

