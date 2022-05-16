package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;

public class ComplexDiscountComponent implements DiscountComponent {
    SimpleDiscountComponent rule;
    Predict predict;

    public ComplexDiscountComponent(SimpleDiscountComponent rule, Predict predict) {
        this.predict = predict;
        this.rule = rule;
    }

    public boolean CanApply(Basket basket) {
        return predict.CanApply(18, basket);
    }

    public double CalculateDiscount(Basket basket) {
        if (CanApply(basket))
            return rule.CalculateDiscount(basket);
        else
            return 0;
    }


}