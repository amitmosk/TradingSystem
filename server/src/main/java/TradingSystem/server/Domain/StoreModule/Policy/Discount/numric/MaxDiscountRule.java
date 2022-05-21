package TradingSystem.server.Domain.StoreModule.Policy.Discount.numric;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;

public class MaxDiscountRule implements DiscountComponent {
    DiscountComponent left;
    DiscountComponent right;

    public MaxDiscountRule(DiscountComponent left, DiscountComponent right) {
        this.left = left;
        this.right = right;
    }

    public double CalculateDiscount(Basket basket) {
        double discount1 = left.CalculateDiscount(basket);
        double discount2 = right.CalculateDiscount(basket);
        return Double.max(discount1, discount2);
    }

    @Override
    public boolean CanApply(Basket basket) {
        return true;
    }
}