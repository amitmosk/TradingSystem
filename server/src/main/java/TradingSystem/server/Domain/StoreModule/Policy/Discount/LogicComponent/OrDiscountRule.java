package TradingSystem.server.Domain.StoreModule.Policy.Discount.LogicComponent;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;

public class OrDiscountRule {
    Predict p1;
    Predict p2;
    SimpleDiscountRule rule;

    public OrDiscountRule(Predict p1, Predict p2, SimpleDiscountRule rule) {
        this.p1 = p1;
        this.p2 = p2;
        this.rule = rule;
    }

    public double CalculatePriceAfterDiscount(Basket basket) {
        double bakset_price = basket.getTotal_price();
        return 5;
//        if (p1.CanBuy(user, product, quantity, bakset_price) | p2.CanBuy(b))
//            return rule.CalculatePriceAfterDiscount(basket);
//        else
//            return b.getTotal_price();
    }
}

// AssignUser user, Product product, int quantity, double price