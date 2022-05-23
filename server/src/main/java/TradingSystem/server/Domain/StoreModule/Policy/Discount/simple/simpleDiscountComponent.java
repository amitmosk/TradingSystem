package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;


public abstract class simpleDiscountComponent implements DiscountComponent {
    protected double percentOfDiscount;

    public simpleDiscountComponent(double percentOfDiscount) throws WrongPermterException {
        checkPrecent(percentOfDiscount);
        this.percentOfDiscount = percentOfDiscount;

    }

    private void checkPrecent(double percentOfDiscount) throws WrongPermterException {
        if (percentOfDiscount < 0 || percentOfDiscount > 1)
            throw new WrongPermterException("the discount is not in the right format(between zero and one)");

    }

    public boolean CanApply(Basket basket) {
        return CalculateDiscount(basket) != 0;
    }

    public abstract double CalculateDiscount(Basket basket);

    public static double ReturnDiscount(double BeforeDiscount, double AfterDiscount) {
        return BeforeDiscount - AfterDiscount;
    }
}
