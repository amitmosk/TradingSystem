package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;

public interface PurchaseRule {

    boolean ConfirmBuying(Basket basket);

}
