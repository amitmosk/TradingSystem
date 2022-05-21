import { StoreReview } from "./StoreReview";
export class User  {
    // private AssignState state;
    // private Cart cart;
    // private AtomicBoolean isGuest;
    // private AtomicBoolean isLogged;
    // private String birth_date;
    
    constructor(data) {
        this.birth_date = data.birth_date
        this.cart =  data.cart
        this.isGuest = data.isGuest
        this.isLogged = data.isLogged
        this.state = data.state
    }
    
    static create(birth_date, cart, isGuest, isLogged, state) {
        return new User({
            birth_date: birth_date,
            cart: cart,
            isGuest: isGuest,
            isLogged: isLogged,
            state: state
        })

    }
}
