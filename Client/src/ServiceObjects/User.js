import { StoreReview } from "./StoreReview";

const GUEST = 0;
const ASSIGN_USER = 1;
const ADMIN = 2;


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
        // this.notifications = [];
    }
    
    static guest(){
        
        return new User({
            state : GUEST,
            email :"",
            name :"Guest",
            lastName : "",
            birth_date : "12-12-2021",
            cart : "",
            storesManaged : [],
            security_question : "what is the time?",
        })
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
            // notifications : [],
        })

    }
    add_store(store_id)
    {
        this.storesManaged.push(store_id);
        console.log("stores of user is = "+this.state.storesManaged)
    }
}
