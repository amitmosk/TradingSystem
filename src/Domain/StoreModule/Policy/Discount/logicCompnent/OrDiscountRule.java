package Domain.StoreModule.Policy.Discount.logicCompnent;


import Domain.StoreModule.Basket;
import Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;
import Domain.StoreModule.Policy.Predict;

public class OrDiscountRule {
    Predict p1;
    Predict p2;
    SimpleDiscountRule rule;

    public OrDiscountRule(Predict p1, Predict p2, SimpleDiscountRule rule) {
        this.p1 = p1;
        this.p2 = p2;
        this.rule = rule;
    }

    public double CalculatePriceAfterDiscount(Basket b) {
        if (p1.CanApply(18, b) | p2.CanApply(18, b))
            return rule.CalculatePriceAfterDiscount(b);
        else
            return b.getTotal_price();
    }
}
