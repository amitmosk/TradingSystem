package Domain.UserModule;

import Domain.StoreModule.Basket;

import java.util.HashMap;
import java.util.Map;

public class Cart implements iCart {
    private Map<Integer, Basket> baskets;                // storeID,Basket

    public Cart() {
        baskets = new HashMap<Integer, Basket>();
    }

    @Override
    public Basket getBasket(int storeID, String email) {
        if (!baskets.containsKey(storeID))
            return new Basket(storeID, email);
        return baskets.get(storeID);
    }

    @Override
    public void addBasket(int storeID, Basket basket) {
        this.baskets.put(storeID, basket);
    }

    @Override
    public void removeBasketIfNeeded(int storeID, Basket storeBasket) {
        if (storeBasket.isEmpty())
            baskets.remove(storeID);
    }

    public Map<Integer, Basket> getBaskets() {
        return baskets;
    }

    @Override
    public void clear() {
        baskets.clear();
    }
}
