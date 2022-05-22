// import axios from "axios";
import { Cart } from "../ServiceObjects/Cart";
import {CONNECTION_ERROR, CATCH, VIEW_USER_CART, BUY_CART } from "./ApiPaths";
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
                return Response.create(cart, response.was_exception, response.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    buy_cart(payment_info, supply_info) {
        return instance.get(BUY_CART,
            {
                params:{ payment_info: payment_info,
                    supply_info: supply_info,}
               
            })
            .then(res => {
                const user_purchase = new UserPurchase(res.data.value)
                return Response.create(user_purchase, res.data.was_exception, res.data.message);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
    
    
   
}