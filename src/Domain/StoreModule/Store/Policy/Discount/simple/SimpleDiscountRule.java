package Domain.StoreModule.Policy.Discount.simple;

import Domain.StoreModule.Basket;
import Domain.StoreModule.Policy.Discount.DiscountRule;
import Domain.StoreModule.Product.Product;
import Domain.UserModule.User;


public abstract class SimpleDiscountRule implements DiscountRule {
    protected float percentOfDiscount;

    public SimpleDiscountRule(float percentOfDiscount) {
        this.percentOfDiscount = percentOfDiscount;
    }

    public boolean CanApply(Basket basket) {
        return true;
    }

    public abstract double CalculatePriceAfterDiscount(Basket basket);

    public static double ReturnDiscount(double BeforeDiscount, double AfterDiscount) {
        return BeforeDiscount - AfterDiscount;
    }
}
