package TradingSystem.server.Domain.StoreModule.Policy;

import TradingSystem.server.Domain.StoreModule.Product.Product;

import java.util.List;

public interface Rule {
    boolean rule(List<Product> products);
}
