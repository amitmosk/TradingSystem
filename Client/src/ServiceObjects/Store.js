import { StoreReview } from "./StoreReview";
export class Store  {
    store_id;
    founder_email;
    name;
    foundation_date;
    inventory; // product & quantity
    storeReview;
    
    constructor(data) {
    this.store_id = data.store_id;
    this.founder_email = data.founder_email;
    this.name = data.name;
    this.foundation_date = data.foundation_date;
    this.inventory = data.inventory; // product & quantity
    this.storeReview = data.storeReview;
    }
    
    static create(store_id, founder_email, name, foundation_date, inventory, storeReview) {
        return new Store({
            store_id: store_id,
            founder_email: founder_email,
            name: name,
            foundation_date:foundation_date,
            inventory : inventory,
            storeReview: storeReview
        })
    }
}
