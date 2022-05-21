// import axios from "axios";
import { Cart } from "../ServiceObjects/Cart";
import { VIEW_USER_CART, BUY_CART } from "./ApiPaths";
import { Response } from "./Response";
import {UserPurchase} from "../ServiceObjects/UserPurchase"

// const instance = axios.create(
//     {withCredentials : true}
// );
const response_obj = new Response("","");
const instance = require('axios');

export class CartApi {

    view_user_cart() {
        return instance.get(VIEW_USER_CART)
            .then(res => {
                let response = res.data;
                let cart = new Cart(response.value) ;
                return Response.create(cart, response.wasException, response.message);
            })
            .catch(res => undefined);
    }

    buy_cart(payment_info, supply_info) {
        return instance.get(BUY_CART,
            {
                params:{ payment_info: payment_info,
                    supply_info: supply_info,}
               
            })
            .then(res => {
                const user_purchase = new UserPurchase(res.data.value)
                return Response.create(user_purchase, res.data.wasException, res.data.message);
            })
            .catch(res => undefined);
    }
    
    
   
}