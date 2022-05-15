package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountRule;


public abstract class SimpleDiscountRule implements DiscountRule {
    protected float percentOfDiscount;

    public SimpleDiscountRule(float percentOfDiscount) {
        this.percentOfDiscount = percentOfDiscount;
    }

    public boolean CanApply(Basket basket) {
        return true;
    }

    public abstract double CalculateDiscount(Basket basket);

    public static double ReturnDiscount(double BeforeDiscount, double AfterDiscount) {
        return BeforeDiscount - AfterDiscount;
    }
}
