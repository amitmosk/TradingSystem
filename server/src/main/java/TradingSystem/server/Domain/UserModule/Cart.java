package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Purchase.Purchase;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.Utils.Exception.*;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Store, Basket> baskets;                // storeID,Basket

    public Cart() {
        baskets = new HashMap<>();
    }

    // ------------------------------ setters ------------------------------
    public void setBaskets(Map<Store, Basket> baskets) {
        this.baskets = baskets;
    }

    // ------------------------------ methods ------------------------------
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

    public void remove_product_from_cart(Store store, Product p) throws MarketException {
        if (!this.baskets.containsKey(store))
            throw new BasketException("user dont have item's from specified store");
        Basket basket = baskets.get(store);
        basket.removeProduct(p);
        if (basket.isEmpty()) baskets.remove(store);
    }

    /**
     * this method served both regular add product to cart & add product after confirm bid offer.
     * @param store
     * @param p
     * @param quantity
     * @param email
     * @param price_per_unit -
     * @throws MarketException
     */
    public void add_product_to_cart(Store store, Product p, int quantity, String email, double price_per_unit) throws MarketException {
        Basket basket = baskets.getOrDefault(store, new Basket(store.getStore_id(), email));
        basket.addProduct(p, quantity, price_per_unit);
        this.baskets.put(store, basket);
    }

    public void edit_product_quantity_in_cart(Store store, Product p, int quantity) throws MarketException {
        if (!baskets.containsKey(store))
            throw new NoUserRegisterdException("user haven't bought product from this store.");
        baskets.get(store).changeQuantity(p, quantity);
    }

    public double check_cart_available_products_and_calc_price(int user_age) throws MarketException {
        double cart_price = 0;
        for (Map.Entry<Store, Basket> entry : baskets.entrySet()) {
            Store store = entry.getKey();
            Basket basket = entry.getValue();
            if (!store.is_active()) throw new PurchaseException("store " + store.getStore_id() + " is not active");
            double basket_price = store.check_available_products_and_calc_price(user_age, basket); // throw if not available
            cart_price += basket_price;
        }
        return cart_price;
    }

    public Map<Integer, Purchase> update_stores_inventory(int purchase_id) throws MarketException {
        Map<Integer, Purchase> store_id_purchase = new HashMap<>();
        for (Map.Entry<Store, Basket> entry : baskets.entrySet()) {
            Store store = entry.getKey();
            Basket basket = entry.getValue();
            Purchase purchase = store.remove_basket_products_from_store(basket, purchase_id);
            store_id_purchase.put(store.getStore_id(), purchase);
        }
        return store_id_purchase;
    }

    public void verify_not_empty() throws BasketException {
        if (this.baskets.size() == 0)
            throw new BasketException("user try to buy empty cart");
    }

    public CartInformation cartInformation(){
        HashMap<StoreInformation, Basket> answer = new HashMap<>();
        for (Map.Entry<Store,Basket> entry : this.baskets.entrySet()){
            StoreInformation temp = new StoreInformation(entry.getKey());
            answer.put(temp, entry.getValue());
        }
        return new CartInformation(answer);
    }
}
