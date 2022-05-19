

export class Cart  {
    
        
        constructor(data) {
            // private Map<Integer, Basket> baskets;                // storeID,Basket
            this.baskets = data.baskets;
    
        }
        
        static create(baskets) {
            return new Cart({
                baskets:baskets
            })
    
        }
    }