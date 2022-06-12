package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("3")
public class simpleDiscountComponentByStore extends simpleDiscountComponent {
    public simpleDiscountComponentByStore(double PrecentOfDiscount) throws WrongPermterException {
        super(PrecentOfDiscount);
    }

    public simpleDiscountComponentByStore() {

    }

    @Override
    public double CalculateDiscount(Basket basket) {
        return basket.getTotal_price() * percentOfDiscount;
    }
}
