package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountComponent;

public class OrDiscountComponent implements DiscountComponent {
    DiscountComponent rule1;
    DiscountComponent rule2;
    SimpleDiscountComponent discount;

    public OrDiscountComponent(DiscountComponent rule1, DiscountComponent rule2, SimpleDiscountComponent Discountrule) {
        this.rule1 = rule1;
        this.rule2 = rule2;
        discount = Discountrule;
    }


    @Override
    public double CalculateDiscount(Basket basket) {
        if (rule1.CanApply(basket) || rule2.CanApply(basket))
            return discount.CalculateDiscount(basket);
        return 0;
    }

    @Override
    public boolean CanApply(Basket basket) {
        return rule1.CanApply(basket) || rule2.CanApply(basket);
    }
}
