package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;

public class DiscountRuleByStore extends SimpleDiscountRule {
    public DiscountRuleByStore(float PrecentOfDiscount) {
        super(PrecentOfDiscount);
    }

    @Override
    public double CalculatePriceAfterDiscount(Basket basket) {
        return basket.getTotal_price() - basket.getTotal_price() * percentOfDiscount;
    }
}
