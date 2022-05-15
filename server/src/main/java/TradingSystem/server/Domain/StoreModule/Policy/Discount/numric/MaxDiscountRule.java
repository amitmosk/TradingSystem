package Domain.StoreModule.Policy.Discount.numric;

import Domain.StoreModule.Basket;
import Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;

public class MaxDiscountRule {
    SimpleDiscountRule simple1;
    SimpleDiscountRule simple2;

    public MaxDiscountRule(SimpleDiscountRule simple1, SimpleDiscountRule simple2) {
        this.simple1 = simple1;
        this.simple2 = simple2;
    }

    public double CalculatePriceAfterDiscount(Basket basket) {
        double discount1 = simple1.CalculatePriceAfterDiscount(basket);
        double discount2 = simple2.CalculatePriceAfterDiscount(basket);
        return Double.max(discount1, discount2);
    }
}