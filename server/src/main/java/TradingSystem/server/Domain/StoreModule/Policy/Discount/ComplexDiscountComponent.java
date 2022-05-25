package TradingSystem.server.Domain.StoreModule.Policy.Discount;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;

public class ComplexDiscountComponent implements DiscountComponent {
    DiscountComponent rule;
    Ipredict predict;

    public ComplexDiscountComponent(DiscountComponent rule, Ipredict predict) {
        this.predict = predict;
        this.rule = rule;
    }

    public boolean CanApply(Basket basket) {
        return predict.CanApply(basket);
    }

    public double CalculateDiscount(Basket basket) {
        if (CanApply(basket))
            return rule.CalculateDiscount(basket);
        else
            return 0;
    }
}