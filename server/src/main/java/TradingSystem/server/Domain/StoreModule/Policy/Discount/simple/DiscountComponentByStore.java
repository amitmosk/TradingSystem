package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

public class DiscountComponentByStore extends SimpleDiscountComponent {
    public DiscountComponentByStore(double PrecentOfDiscount) throws WrongPermterException {
        super(PrecentOfDiscount);
    }

    @Override
    public double CalculateDiscount(Basket basket) {
        return basket.getTotal_price() * percentOfDiscount;
    }
}
