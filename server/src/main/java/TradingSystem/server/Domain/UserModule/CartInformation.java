package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;

import java.util.HashMap;
import java.util.Map;

public class CartInformation {
    private Map<StoreInformation, Basket> baskets;

    public CartInformation(){}


    public CartInformation(HashMap<StoreInformation, Basket> basketHashMap){
        this.baskets = basketHashMap;
    }

    public void setBaskets(Map<StoreInformation, Basket> baskets) {
        this.baskets = baskets;
    }

    public Map<StoreInformation, Basket> getBaskets() {
        return baskets;
    }
}
