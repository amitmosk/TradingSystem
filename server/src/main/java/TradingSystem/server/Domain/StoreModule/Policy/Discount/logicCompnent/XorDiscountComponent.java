package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountComponent;

public class XorDiscountComponent implements DiscountComponent {
    DiscountComponent rule1;
    DiscountComponent rule2;
    SimpleDiscountComponent discount;

    public XorDiscountComponent(DiscountComponent rule1, DiscountComponent rule2, SimpleDiscountComponent Discountrule) {
        this.rule1 = rule1;
        this.rule2 = rule2;
        discount = Discountrule;
    }


    @Override
    public double CalculateDiscount(Basket basket) {
        double discountFromLeft = rule1.CalculateDiscount(basket);
        double discountFromRight = rule2.CalculateDiscount(basket);
        boolean XorRes = (discountFromLeft != 0) ^ (discountFromRight != 0);
        if (XorRes)//if XorRes==true then only one discount is working
            return Math.max(discountFromLeft, discountFromRight);
        else return Math.min(discountFromLeft, discountFromRight);
    }

    @Override
    public boolean CanApply(Basket basket) {
        return (rule1.CalculateDiscount(basket) != 0) || (rule2.CalculateDiscount(basket) != 0);
    }
}
