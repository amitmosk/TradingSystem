package Domain.UserModule;

import Domain.StoreModule.Basket;

public interface iCart {
    Basket getBasket(int storeID, String email);

    void addBasket(int storeID, Basket basket);

    void removeBasketIfNeeded(int storeID, Basket storeBasket);

    void clear();
}
