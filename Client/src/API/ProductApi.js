// import axios from "axios";
import {EMPLOYEE_BASE_REST_API_URL,  FIND_PRODUCT_INFORMATION, FIND_PRODUCTS_BY_NAME, FIND_PRODUCTS_BY_CATEGORY,FIND_PRODUCTS_BY_KEYWORDS 
    ,ADD_PRODUCT_TO_CART, EDIT_PRODUCT_QUANTITY_IN_CART,REMOVE_PRODUCT_FROM_CART, ADD_PRODUCT_REVIEW, RATE_PRODUCT, EDIT_PRODUCT_NAME, EDIT_PRODUCT_PRICE, EDIT_PRODUCT_CATEGORY, EDIT_PRODUCT_KEY_WORDS, LOGIN_PATH} from "./ApiPaths";
import { Response } from "./Response";
import { Product } from "../ServiceObjects/Product";
// const instance = axios.create(
//     {withCredentials : true}
// );
const instance = require('axios');

const response_obj = new Response("","");



export class ProductApi {
    testtttt(a) {
        return instance.get(EMPLOYEE_BASE_REST_API_URL, 
            {
                params:{a : a}
            })
            .then(res => {
                console.log("this is the answer\n\n"+res.data.value)
                return new Response(res.data)
            })
            .catch(res => console.log("fuckkkkk\n\n"));
    }
    find_product_information(product_id) {
        return instance.get(FIND_PRODUCT_INFORMATION, 
            {
                params:{product_id: product_id,}
            })
            .then(res => {
                let response = res.data;
                let product_info = new Product(response.value);
                return Response.create(product_info, response.wasException, response.message);
            })
            .catch(res => undefined);
    }
    find_products_by_name(product_name) {
        return instance.get(FIND_PRODUCTS_BY_NAME,
            {
                params:{product_name: product_name,}
                
            })
            .then(res => {
                let response = res.data;
                //traverse the products and create product for each element on the list
                //create response with the list of products
                const arr = [];
                res.data.value.map(p => arr.push(new Product(p)));
                return Response.create(arr,res.data.wasException,res.data.message);
            })
            .catch(res => undefined);
    }

    find_products_by_category(product_category) {
        return instance.get(FIND_PRODUCTS_BY_CATEGORY,
            {
                params:{product_category: product_category,}
                
            })
            .then(res => {
                //traverse the products and create product for each element on the list
                //create response with the list of products
                const arr = [];
                res.data.value.map(p => arr.push(new Product(p)));
                return Response.create(arr,res.data.wasException,res.data.message);
            })
            .catch(res => undefined);
    }
    find_products_by_keywords(product_keywords) {
        return instance.get(FIND_PRODUCTS_BY_KEYWORDS,
            {
                params:{product_keywords: product_keywords,}
                
            })
            .then(res => {
                //traverse the products and create product for each element on the list
                //create response with the list of products
                const arr = [];
                res.data.value.map(p => arr.push(new Product(p)));
                return Response.create(arr,res.data.wasException,res.data.message);
            })
            .catch(res => undefined);
    }

    add_product_to_cart(store_id, product_id, quantity) {
        return instance.get(ADD_PRODUCT_TO_CART,
            {
                params:{store_id: store_id,
                    product_id: product_id,
                    quantity: quantity,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }

    edit_product_quantity_in_cart(store_id, product_id, quantity) {
        return instance.get(EDIT_PRODUCT_QUANTITY_IN_CART,
            {
                params:{store_id: store_id,
                    product_id: product_id,
                    quantity: quantity,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }

    remove_product_from_cart(store_id, product_id) {
        return instance.get(REMOVE_PRODUCT_FROM_CART,
            {
                params:{ store_id: store_id,
                    product_id: product_id,}
               
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
    

    add_product_review(product_id, store_id, review) {
        return instance.get(ADD_PRODUCT_REVIEW,
            {
                params:{  product_id: product_id,
                    store_id: store_id,
                    review : review,}
               
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }

    rate_product(product_id, store_id, rate) {
        return instance.get(RATE_PRODUCT,
            {
                params:{ product_id: product_id,
                    store_id: store_id,
                    rate : rate,}
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }

    edit_product_name(product_id, store_id, name) {
        return instance.get(EDIT_PRODUCT_NAME,
            {
                params:{product_id: product_id,
                    store_id: store_id,
                    name : name,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }

    edit_product_price(product_id, store_id, price) {
        return instance.get(EDIT_PRODUCT_PRICE,
            {
                params:{product_id: product_id,
                    store_id: store_id,
                    price : price,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
    edit_product_category(product_id, store_id, category) {
        return instance.get(EDIT_PRODUCT_CATEGORY,
            {
                params:{product_id: product_id,
                    store_id: store_id,
                    category : category,}
                
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
    edit_product_key_words(product_id, store_id, key_words) {
        return instance.get(EDIT_PRODUCT_KEY_WORDS,
            {
                params:{ product_id: product_id,
                    store_id: store_id,
                    key_words : key_words,}
               
            })
            .then(res => {
                return new Response(res.data)
            })
            .catch(res => undefined);
    }
}