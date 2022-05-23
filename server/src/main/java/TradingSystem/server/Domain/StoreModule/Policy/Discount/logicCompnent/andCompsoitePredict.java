package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;

import java.util.List;

public class andCompsoitePredict extends CompositePredict {
    Ipredict left;
    Ipredict right;

    public andCompsoitePredict(Ipredict left, Ipredict right) {
        super(left, right);
    }


    @Override
    public boolean CanApply(Basket basket) {
        return left.CanApply(basket) && right.CanApply(basket);

    }
}
