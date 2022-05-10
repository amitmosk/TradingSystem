package Domain.StoreModule.Policy.Discount.simple;

import Domain.StoreModule.Basket;
import Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;

public class DiscountRuleByStore extends SimpleDiscountRule {
    public DiscountRuleByStore(float PrecentOfDiscount) {
        super(PrecentOfDiscount);
    }

    @Override
    public double CalculatePriceAfterDiscount(Basket basket) {
        return basket.getTotal_price() - basket.getTotal_price() * percentOfDiscount;
    }
}
