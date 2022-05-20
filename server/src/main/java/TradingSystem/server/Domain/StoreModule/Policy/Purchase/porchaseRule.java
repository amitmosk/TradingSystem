package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;

public interface porchaseRule {
    boolean predictCheck(int age, Basket basket);


}
