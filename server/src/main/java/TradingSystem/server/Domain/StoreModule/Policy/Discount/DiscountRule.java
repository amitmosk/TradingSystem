package TradingSystem.server.Domain.StoreModule.Policy.Discount;
//package Domain.StoreModule.Policy.Discount;

import TradingSystem.server.Domain.StoreModule.Basket;

public interface DiscountRule {
    double CalculatePriceAfterDiscount(Basket basket);
    boolean CanApply(Basket basket);
}
