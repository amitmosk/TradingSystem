package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;
import TradingSystem.server.Domain.StoreModule.Product.Product;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;
@Entity
@DiscriminatorValue("3")
public class andCompsoitePredict extends CompositePredict {

    public andCompsoitePredict(Ipredict left, Ipredict right) {
        super(left, right);
    }

    public andCompsoitePredict() {

    }


    @Override
    public boolean CanApply(Basket basket) {
        return left.CanApply(basket) && right.CanApply(basket);

    }
}
