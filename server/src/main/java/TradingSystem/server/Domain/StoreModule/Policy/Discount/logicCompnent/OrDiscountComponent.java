package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;

import java.util.List;

public class OrDiscountComponent extends DiscountlogicComponent implements DiscountComponent {


    public OrDiscountComponent(List<DiscountComponent> list, ComplexDiscountComponent discount) {
        super(list, discount);
    }


    @Override
    public double CalculateDiscount(Basket basket) {
        for (DiscountComponent compnent : componentsList) {
            if (compnent.CanApply(basket))
                discount.CalculateDiscount(basket);
        }
        return 0;
    }

    @Override
    public boolean CanApply(Basket basket) {
        for (DiscountComponent compnent : componentsList) {
            if (compnent.CanApply(basket))
                return true;
        }
        return false;
    }
}
