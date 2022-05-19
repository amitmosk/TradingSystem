

export class UserPurchase  {
    
        
    constructor(data) {
        // private int purchase_id;
        // private Map<Integer, Purchase> store_id_purchase;
        // private double total_price;
        this.purchase_id = data.purchase_id;
        this.store_id_purchase = data.store_id_purchase;
        this.total_price = data.total_price;

    }
    
    static create(purchase_id,store_id_purchase, total_price) {
        return new UserPurchase({
            purchase_id:purchase_id,
            store_id_purchase:store_id_purchase,
            total_price:total_price
        })

    }
}