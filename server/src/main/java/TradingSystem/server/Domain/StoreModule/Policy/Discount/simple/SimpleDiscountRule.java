package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountRule;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.UserModule.User;


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
