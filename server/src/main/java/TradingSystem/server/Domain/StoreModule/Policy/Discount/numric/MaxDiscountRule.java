package TradingSystem.server.Domain.StoreModule.Policy.Discount.numric;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;

public class MaxDiscountRule {
    SimpleDiscountRule simple1;
    SimpleDiscountRule simple2;

    public MaxDiscountRule(SimpleDiscountRule simple1, SimpleDiscountRule simple2) {
        this.simple1 = simple1;
        this.simple2 = simple2;
    }

    public double CalculateDiscount(Basket basket) {
        double discount1 = simple1.CalculateDiscount(basket);
        double discount2 = simple2.CalculateDiscount(basket);
        return Double.max(discount1, discount2);
    }
}