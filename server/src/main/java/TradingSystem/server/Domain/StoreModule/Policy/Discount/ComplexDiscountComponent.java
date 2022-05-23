package TradingSystem.server.Domain.StoreModule.Policy.Discount;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;

public class ComplexDiscountComponent implements DiscountComponent {
    simpleDiscountComponent rule;
    Predict predict;

    public ComplexDiscountComponent(simpleDiscountComponent rule, Predict predict) {
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