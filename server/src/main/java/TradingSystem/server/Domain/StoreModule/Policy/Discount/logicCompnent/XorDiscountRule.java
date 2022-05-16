package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountRule;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;

public class XorDiscountRule implements DiscountRule {
    private SimpleDiscountRule Left;
    private SimpleDiscountRule Right;


    public XorDiscountRule(SimpleDiscountRule Left, SimpleDiscountRule Right) {
        this.Left = Left;
        this.Right = Right;
    }

    @Override
    public double CalculateDiscount(Basket basket) {
        double discountFromLeft = Left.CalculateDiscount(basket);
        double discountFromRight = Right.CalculateDiscount(basket);
        boolean XorRes = (discountFromLeft != 0) ^ (discountFromRight != 0);
        if (XorRes)//if XorRes==true then only one discount is working
            return Math.max(discountFromLeft, discountFromRight);
        else return Math.min(discountFromLeft, discountFromRight);
    }

    @Override
    public boolean CanApply(Basket basket) {
        return true;
    }
}
