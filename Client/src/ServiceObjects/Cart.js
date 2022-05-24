

export class Cart  {
    
        
        constructor(data) {
            // private Map<Integer, Basket> baskets;                // storeID,Basket
            this.products = data.products;
            this.price = data.price;
        }
        
        static create(baskets) {
            return new Cart({
                products:baskets.products,
                price:baskets.price
            })
    
        }
    }