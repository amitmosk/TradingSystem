package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "simple_discount_type",
        discriminatorType = DiscriminatorType.INTEGER)
public abstract class simpleDiscountComponent extends DiscountComponent {
    protected double percentOfDiscount;

    public simpleDiscountComponent(double percentOfDiscount) throws WrongPermterException {
        checkPrecent(percentOfDiscount);
        this.percentOfDiscount = percentOfDiscount;

    }

    public simpleDiscountComponent() {

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
