// import axios from "axios";
import {CONNECTION_ERROR, CATCH, ADD_SIMPLE_CATEGORY_DISCOUNT, ADD_SIMPLE_PRODUCT_DISCOUNT, ADD_SIMPLE_STORE_DISCOUNT, 
    ADD_COMPLEX_DISCOUNT, ADD_COMPLEX_AND_DISCOUNT, ADD_COMPLEX_OR_DISCOUNT, 
    ADD_COMPLEX_MAX_DISCOUNT, ADD_COMPLEX_PLUS_DISCOUNT, ADD_COMPLEX_XOR_DISCOUNT, SEND_PREDDICTS, GET_DISCOUNT_POLICY} from "./ApiPaths";
import { Response } from "./Response";
import { Store } from "../ServiceObjects/Store";
import { Product } from "../ServiceObjects/Product";
// const instance = axios.create(
//     {withCredentials : true}
// );
var qs = require('qs');
const instance = require('axios');

export class PoliciesApi {
    add_simple_categorey_discount_rule(store_id, name, precent, nameOfRule ){
        return instance.get(ADD_SIMPLE_CATEGORY_DISCOUNT,
            {
                params:{
                    store_id : store_id,
                    name : name,
                    precent : precent,
                    nameOfRule : nameOfRule,
                 }
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        }

    add_simple_product_discount_rule(store_id, id, precent, nameOfRule ){
    return instance.get(ADD_SIMPLE_PRODUCT_DISCOUNT,
        {
            params:{
                store_id : store_id,
                id : id,
                precent : precent,
                nameOfRule : nameOfRule,
                }
        })
        .then(res => {
            return new Response(res.data)
        })
        .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    add_simple_store_discount_rule(store_id, precent, nameOfRule ){
    return instance.get(ADD_SIMPLE_STORE_DISCOUNT,
        {
            params:{
                store_id : store_id,
                precent : precent,
                nameOfRule : nameOfRule,
                }
        })
        .then(res => {
            return new Response(res.data)
        })
        .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    add_complex_discount_rule(store_id, nameOfPredict, nameOfPolicy, nameOfRule ){
    return instance.get(ADD_COMPLEX_DISCOUNT,
        {
            params:{
                store_id : store_id,
                nameOfPredict : nameOfPredict,
                nameOfPolicy : nameOfPolicy,
                nameOfRule : nameOfRule,
                }
        })
        .then(res => {
            return new Response(res.data)
        })
        .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }


    add_and_discount_rule(left, right, store_id,  nameOfRule ){
    return instance.get(ADD_COMPLEX_AND_DISCOUNT,
        {
            params:{
                left : left,
                right : right,
                store_id : store_id,
                nameOfRule : nameOfRule,
                }
        })
        .then(res => {
            return new Response(res.data)
        })
        .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    add_or_discount_rule(left, right, store_id,  nameOfRule ){
    return instance.get(ADD_COMPLEX_OR_DISCOUNT,
        {
            params:{
                left : left,
                right : right,
                store_id : store_id,
                nameOfRule : nameOfRule,
                }
        })
        .then(res => {
            return new Response(res.data)
        })
        .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }


    add_max_discount_rule(left, right, store_id,  nameOfRule ){
    return instance.get(ADD_COMPLEX_MAX_DISCOUNT,
        {
            params:{
                left : left,
                right : right,
                store_id : store_id,
                nameOfRule : nameOfRule,
                }
        })
        .then(res => {
            return new Response(res.data)
        })
        .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    add_plus_discount_rule(left, right, store_id,  nameOfRule ){
    return instance.get(ADD_COMPLEX_PLUS_DISCOUNT,
        {
            params:{
                left : left,
                right : right,
                store_id : store_id,
                nameOfRule : nameOfRule,
                }
        })
        .then(res => {
            return new Response(res.data)
        })
        .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }


    add_xor_discount_rule(left, right, store_id,  nameOfRule ){
        return instance.get(ADD_COMPLEX_XOR_DISCOUNT,
            {
                params:{
                    left : left,
                    right : right,
                    store_id : store_id,
                    nameOfRule : nameOfRule,
                    }
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        }


            send_predicts(store_id){
            return instance.get(SEND_PREDDICTS,
                {
                    params:{
                        store_id : store_id,
                        }
                })
                .then(res => {
                    return new Response(res.data)
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
            }

                get_discount_policy(store_id){
                return instance.get(GET_DISCOUNT_POLICY,
                    {
                        params:{
                            store_id : store_id,
                            }
                    })
                    .then(res => {
                        return new Response(res.data)
                    })
                    .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
                }


                
         



            
            
          
    
           
    
    
           
        

   

       
   

   
}

