package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("4")
public class xorDiscountComponent extends DiscountComponent {
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    DiscountComponent left;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    DiscountComponent right;

    public DiscountComponent getLeft() {
        return left;
    }

    public void setLeft(DiscountComponent left) {
        this.left = left;
    }

    public DiscountComponent getRight() {
        return right;
    }

    public void setRight(DiscountComponent right) {
        this.right = right;
    }

    public xorDiscountComponent(DiscountComponent left, DiscountComponent right) throws WrongPermterException {
        this.right = right;
        this.left = left;
    }

    public xorDiscountComponent() {

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
