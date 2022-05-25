package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;

public class OrCompositePredict extends CompositePredict  {

    public OrCompositePredict(Ipredict left, Ipredict right) {
        super(left, right);
    }

    @Override
    public boolean CanApply(Basket basket) {
        return left.CanApply(basket) || right.CanApply(basket);
    }
}
