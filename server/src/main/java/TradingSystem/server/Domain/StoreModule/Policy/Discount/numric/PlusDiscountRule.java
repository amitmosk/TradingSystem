package TradingSystem.server.Domain.StoreModule.Policy.Discount.numric;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountRule;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;

public class PlusDiscountRule {
    DiscountRule simple1;
    DiscountRule simple2;

    public PlusDiscountRule(DiscountRule simple1, DiscountRule simple2) {
        this.simple1 = simple1;
        this.simple2 = simple2;
    }

    public double CalculateDiscount(Basket basket) {
        double discount1 = simple1.CalculateDiscount(basket);
        double discount2 = simple2.CalculateDiscount(basket);
        return discount1 + discount2;
    }
}