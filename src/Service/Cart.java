package Service;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Integer,Basket> baskets;                // storeID,Basket

    public Basket getBasket(int storeID) {
        if(!baskets.containsKey(storeID))
            return new Basket();
        return baskets.get(storeID);
    }

    public void addBasket(int storeID, Basket basket) {
        this.baskets.put(storeID,basket);
    }

    public void removeBasketIfNeeded(int storeID, Basket storeBasket) {
        if(storeBasket.isEmpty())
            baskets.remove(storeID);
    }

    public Map<Integer,Basket> getBaskets(){
        return baskets;
    }

    public void clear() {
        baskets.clear();
    }
}
