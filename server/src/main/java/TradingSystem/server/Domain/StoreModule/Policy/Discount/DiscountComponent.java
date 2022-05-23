package TradingSystem.server.Domain.StoreModule.Policy.Discount;


import TradingSystem.server.Domain.StoreModule.Basket;

public interface DiscountComponent {
    double CalculateDiscount(Basket basket);

    boolean CanApply(Basket basket);
}
