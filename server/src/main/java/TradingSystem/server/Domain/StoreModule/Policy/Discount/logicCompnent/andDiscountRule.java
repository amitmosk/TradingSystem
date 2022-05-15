package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;

public class andDiscountRule {
    Predict p1;
    Predict p2;
    SimpleDiscountRule rule;

    public andDiscountRule(Predict p1, Predict p2, SimpleDiscountRule rule) {
        this.p1 = p1;
        this.p2 = p2;
        this.rule = rule;
    }

    public double CalculatePriceAfterDiscount(Basket b) {
        if (p1.CanApply(18, b) & p2.CanApply(18, b))
            return rule.CalculateDiscount(b);
        else
            return 0;
    }
}
