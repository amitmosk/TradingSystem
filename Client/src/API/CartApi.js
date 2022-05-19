import axios from "axios";
import { Cart } from "../ServiceObjects/Cart";
import { VIEW_USER_CART, BUY_CART } from "./ApiPaths";
import { Response } from "./Response";

const instance = axios.create(
    {withCredentials : true}
);
const response_obj = new Response();


export class CartApi {

    view_user_cart() {
        return instance.get(VIEW_USER_CART)
            .then(res => {
                let response = res.data;
                let cart = new Cart(response.value) ;
                return response_obj.create(cart, response.message);
            })
            .catch(res => undefined);
    }

    buy_cart(payment_info, supply_info) {
        return instance.post(BUY_CART,
            {
                payment_info: payment_info,
                supply_info: supply_info,
            })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => undefined);
    }
    
    
   
}