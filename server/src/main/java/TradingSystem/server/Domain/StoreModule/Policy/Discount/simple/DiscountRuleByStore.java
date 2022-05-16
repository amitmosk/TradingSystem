package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

public class DiscountRuleByStore extends SimpleDiscountRule {
    public DiscountRuleByStore(double PrecentOfDiscount) throws WrongPermterException {
        super(PrecentOfDiscount);
    }

    @Override
    public double CalculateDiscount(Basket basket) {
        return basket.getTotal_price() * percentOfDiscount;
    }
}
