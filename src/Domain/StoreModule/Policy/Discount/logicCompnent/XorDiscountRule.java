package Domain.StoreModule.Policy.Discount.logicCompnent;

import Domain.StoreModule.Basket;
import Domain.StoreModule.Policy.Discount.DiscountRule;
import Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;
import Domain.StoreModule.Policy.Predict;

public class XorDiscountRule implements DiscountRule {
    private Predict Left;
    private Predict Right;
    private SimpleDiscountRule DiscountRule;

    public XorDiscountRule(Predict Left, Predict Right, SimpleDiscountRule rule) {
        this.Left = Left;
        this.Right = Right;
        DiscountRule = rule;
    }

    @Override
    public double CalculatePriceAfterDiscount(Basket basket) {
        boolean LeftRes = Left.CanApply(basket);
        boolean RightRes = Right.CanApply(basket);
        boolean XorRes = LeftRes ^ RightRes;
        if (XorRes)
    }

    @Override
    public boolean CanApply(Basket basket) {
        boolean Leftres = Left.CanApply(basket);
        boolean Rightres = Right.CanApply(basket);
        return Leftres ^ Rightres;
    }
}
