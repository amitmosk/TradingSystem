package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;


import TradingSystem.server.Domain.StoreModule.Basket;

public class DiscountRuleByStore extends SimpleDiscountRule {
    public DiscountRuleByStore(float PrecentOfDiscount) {
        super(PrecentOfDiscount);
    }

    @Override
    public double CalculateDiscount(Basket basket) {
        return basket.getTotal_price() * percentOfDiscount;
    }
}
