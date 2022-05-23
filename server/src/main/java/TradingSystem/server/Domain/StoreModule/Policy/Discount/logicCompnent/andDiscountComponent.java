package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;

import java.util.List;

public class andDiscountComponent extends abstractDiscountlogicComponent implements DiscountComponent {

    public andDiscountComponent(List<DiscountComponent> list, ComplexDiscountComponent Discountrule) {
        super(list, Discountrule);
    }


    @Override
    public double CalculateDiscount(Basket basket) {
        for (DiscountComponent compnent : componentsList) {
            if (!compnent.CanApply(basket))
                return 0;
        }
        return discount.CalculateDiscount(basket);
    }

    @Override
    public boolean CanApply(Basket basket) {
        for (DiscountComponent compnent : componentsList) {
            if (!compnent.CanApply(basket))
                return false;
        }
        return true;
    }
}
