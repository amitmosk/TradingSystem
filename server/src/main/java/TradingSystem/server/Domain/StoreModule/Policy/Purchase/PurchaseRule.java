package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;

public abstract class PurchaseRule {
    public abstract boolean predictCheck(int age, Basket basket);


}
