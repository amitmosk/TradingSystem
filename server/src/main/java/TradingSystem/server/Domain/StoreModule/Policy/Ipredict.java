package TradingSystem.server.Domain.StoreModule.Policy;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;

import java.util.Map;

public interface Ipredict {

    public boolean CanApply(Basket b);
}
