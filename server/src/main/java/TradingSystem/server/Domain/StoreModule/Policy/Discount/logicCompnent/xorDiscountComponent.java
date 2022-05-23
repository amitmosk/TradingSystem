package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

public class xorDiscountComponent implements DiscountComponent {
    DiscountComponent left;
    DiscountComponent right;

    public xorDiscountComponent(DiscountComponent left, DiscountComponent right) throws WrongPermterException {
        this.right = right;
        this.left = left;
    }


    @Override
    public double CalculateDiscount(Basket basket) {
        double discountFromLeft = left.CalculateDiscount(basket);
        double discountFromRight = right.CalculateDiscount(basket);
        boolean XorRes = (discountFromLeft != 0) ^ (discountFromRight != 0);
        if (XorRes)//if XorRes==true then only one discount is working
            return Math.max(discountFromLeft, discountFromRight);
        else return Math.min(discountFromLeft, discountFromRight);
    }

    @Override
    public boolean CanApply(Basket basket) {
        return left.CanApply(basket) || right.CanApply(basket);
    }
}
