import { StoreReview } from "./StoreReview";
export class User  {
    // private AssignState state;
    // private Cart cart;
    // private AtomicBoolean isGuest;
    // private AtomicBoolean isLogged;
    // private String birth_date;
    
    constructor(data) {
        this.founder_email = data.founder_email;
        this.store_name = data.store_name;
        this.foundation_date = data.foundation_date
        this.storeReviewInformation = new StoreReview(data.StoreReviewInformation)

    }
    
    static create(founder_email, store_name, foundation_date, storeReviewInformation) {
        return new User({
            founder_email: founder_email,
            store_name: store_name,
            foundation_date: foundation_date,
            storeReviewInformation:storeReviewInformation
        })

    }
}
