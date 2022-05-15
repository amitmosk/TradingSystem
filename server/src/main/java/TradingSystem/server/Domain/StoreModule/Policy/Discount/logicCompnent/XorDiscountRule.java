package Domain.StoreModule.Policy.Discount.logicCompnent;

import Domain.StoreModule.Basket;
import Domain.StoreModule.Policy.Discount.DiscountRule;
import Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;
import Domain.StoreModule.Policy.Predict;

public class XorDiscountRule implements DiscountRule {
    private SimpleDiscountRule Left;
    private SimpleDiscountRule Right;

    public XorDiscountRule(SimpleDiscountRule Left, SimpleDiscountRule Right) {
        this.Left = Left;
        this.Right = Right;
    }

    @Override
    public double CalculatePriceAfterDiscount(Basket basket) {
        double discountFromLeft = Left.CalculatePriceAfterDiscount(basket);
        double discountFromRight = Right.CalculatePriceAfterDiscount(basket);
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
