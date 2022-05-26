

export class Basket  {
    
        
    constructor(data) {
        // private Pair<String, Integer> basket_id; // <user_email, store_id>
        // private Map<Product, Integer> products_and_quantities; //  product & quantity
        // private double total_price;
        this.basket_id = data.basket_id;
        this.products_and_quantities = data.products_and_quantities;
        this.total_price = data.total_price;

    }
    
    static create(basket_id, products_and_quantities, total_price) {
        return new Basket({
            basket_id:basket_id,
            products_and_quantities:products_and_quantities,
            total_price:total_price
        })

    }
}