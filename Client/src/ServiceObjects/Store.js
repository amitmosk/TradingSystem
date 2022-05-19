import { StoreReviewInformation } from "./StoreReview";
export class StoreInformation  {
    founder_email;
    store_name;
    foundation_date;
    storeReviewInformation;
    
    constructor(data) {
        this.founder_email = data.founder_email;
        this.store_name = data.store_name;
        this.foundation_date = data.foundation_date
        this.storeReviewInformation = new StoreReviewInformation(data.StoreReviewInformation)

    }
    
    static create(founder_email, store_name, foundation_date, storeReviewInformation) {
        return new StoreInformation({
            founder_email: founder_email,
            store_name: store_name,
            foundation_date: foundation_date,
            storeReviewInformation:storeReviewInformation
        })

    }
}
