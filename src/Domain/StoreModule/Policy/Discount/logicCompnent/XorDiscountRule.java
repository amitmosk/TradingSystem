package Domain.StoreModule.Policy.Discount.logicCompnent;

import Domain.StoreModule.Basket;
import Domain.StoreModule.Policy.Discount.DiscountRule;
import Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;
import Domain.StoreModule.Policy.Predict;

public class XorDiscountRule implements DiscountRule {
    private SimpleDiscountRule Left;
    private SimpleDiscountRule Right;
    private boolean LeftOrRight;//left =true right=false

    public XorDiscountRule(SimpleDiscountRule Left, SimpleDiscountRule Right, boolean LeftOrRight) {
        this.Left = Left;
        this.Right = Right;
        this.LeftOrRight = LeftOrRight;
    }

    @Override
    public double CalculatePriceAfterDiscount(Basket basket) {
        double discountFromLeft = Left.CalculatePriceAfterDiscount(basket);
        double discountFromRight = Right.CalculatePriceAfterDiscount(basket);
        boolean XorRes = discountFromLeft != 0 ^ discountFromRight != 0;
        if (XorRes)
            return Math.max(discountFromLeft, discountFromRight);
        else if(LeftOrRight)//left =true right =false
            return discountFromLeft;
        else
            return discountFromRight;

    }

    @Override
    public boolean CanApply(Basket basket) {
        boolean Leftres = Left.CanApply(basket);
        boolean Rightres = Right.CanApply(basket);
        return Leftres ^ Rightres;
    }
}
