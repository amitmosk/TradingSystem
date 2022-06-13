package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class OrCompositePredict extends CompositePredict  {

    public OrCompositePredict(Ipredict left, Ipredict right) {
        super(left, right);
    }

    public OrCompositePredict() {

    }

    @Override
    public boolean CanApply(Basket basket) {
        return left.CanApply(basket) || right.CanApply(basket);
    }
}
