package TradingSystem.server.Domain.StoreModule.Policy.Discount.LogicComponent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountRule;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;

public  class ComplexDiscountRule implements DiscountRule {
    SimpleDiscountRule rule;
    Predict predict;

    public ComplexDiscountRule(SimpleDiscountRule rule, Predict predict) {
        this.predict = predict;
        this.rule = rule;
    }

    public boolean CanApply(Basket basket) {
        return true;
//        return predict.CanBuy(basket);
    }

    public double CalculatePriceAfterDiscount(Basket basket) {
        if (CanApply(basket))
            return rule.CalculatePriceAfterDiscount(basket);
        else
            return basket.getTotal_price();
    }


}