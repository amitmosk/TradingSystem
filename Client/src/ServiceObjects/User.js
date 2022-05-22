import { StoreReview } from "./StoreReview";
export class User  {
    constructor(data) {
        this.state = data.state;
        this.email = data.email;
        this.name = data.name;
        this.lastName = data.lastName;
        this.birth_date = data.birth_date;
        this.cart = data.cart;
        this.storesManaged = data.storesManaged;
        this.security_question = data.security_question;
    }
    
    static create(state, email, name, lastName, birth_date, cart, storesManaged, security_question) {
        return new User({
            state : state,
            email :email,
            name :name,
            lastName : lastName,
            birth_date : birth_date,
            cart :cart,
            storesManaged : storesManaged,
            security_question : security_question,
        })

    }
}
