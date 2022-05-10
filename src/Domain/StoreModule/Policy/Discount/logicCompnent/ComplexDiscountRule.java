package Domain.StoreModule.Policy.Discount.logicCompnent;

import Domain.StoreModule.Basket;
import Domain.StoreModule.Policy.Discount.DiscountRule;
import Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;
import Domain.StoreModule.Policy.Predict;

public  class ComplexDiscountRule implements DiscountRule {
    SimpleDiscountRule rule;
    Predict predict;

    public ComplexDiscountRule(SimpleDiscountRule rule, Predict predict) {
        this.predict = predict;
        this.rule = rule;
    }

    public boolean CanApply(Basket basket) {
        return predict.CanApply(basket);
    }

    public double CalculatePriceAfterDiscount(Basket basket) {
        if (CanApply(basket))
            return rule.CalculatePriceAfterDiscount(basket);
        else
            return basket.getTotal_price();
    }


}