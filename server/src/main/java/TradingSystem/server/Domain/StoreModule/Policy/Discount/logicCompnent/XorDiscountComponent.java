package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import java.util.List;

public class XorDiscountComponent extends DiscountlogicComponent implements DiscountComponent {


    public XorDiscountComponent(List<DiscountComponent> lst, ComplexDiscountComponent discountComponent) throws WrongPermterException {
        super(lst, discountComponent);
        if (lst.size() != 2)
            throw new WrongPermterException("xor can have only two sons");
    }


    @Override
    public double CalculateDiscount(Basket basket) {
        double discountFromLeft = componentsList.get(0).CalculateDiscount(basket);
        double discountFromRight = componentsList.get(1).CalculateDiscount(basket);
        boolean XorRes = (discountFromLeft != 0) ^ (discountFromRight != 0);
        if (XorRes)//if XorRes==true then only one discount is working
            return Math.max(discountFromLeft, discountFromRight);
        else return Math.min(discountFromLeft, discountFromRight);
    }

    @Override
    public boolean CanApply(Basket basket) {
        return componentsList.get(0).CanApply(basket) || componentsList.get(1).CanApply(basket);
    }
}
