package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.Utils.Exception.MarketException;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Store, Basket> baskets;                // storeID,Basket

    public Cart() {
        baskets = new HashMap<Store, Basket>();
    }

    public Basket getBasket(int storeID, String email) {
        if (!baskets.containsKey(storeID))
            return new Basket(storeID, email);
        return baskets.get(storeID);
    }

    public void addBasket(Store store, Basket basket) {
        this.baskets.put(store, basket);
    }
    
    public void removeBasketIfNeeded(int storeID, Basket storeBasket) {
        if (storeBasket.isEmpty())
            baskets.remove(storeID);
    }

    public Map<Store, Basket> getBaskets() {
        return baskets;
    }

    public void clear() {
        baskets.clear();
    }

    public void remove_product_from_cart(Store store, Product p) throws Exception {
        if(!this.baskets.containsKey(store))
            throw new Exception("user dont have item's from specified store");
        Basket basket = baskets.get(store);
        basket.removeProduct(p);
        if(basket.isEmpty()) baskets.remove(store);
    }

    public void add_product_to_cart(Store store, Product p, int quantity, String email) throws MarketException {
        Basket basket = baskets.getOrDefault(baskets.get(store),new Basket(store.getStore_id(),email));
        basket.addProduct(p,quantity);
        this.baskets.put(store,basket);
    }

    public void edit_product_quantity_in_cart(Store store, Product p, int quantity) throws Exception {
        if(!baskets.containsKey(store))
            throw new Exception("user havn't bought product from this store.");
        baskets.get(store).changeQuantity(p,quantity);

    }
}
