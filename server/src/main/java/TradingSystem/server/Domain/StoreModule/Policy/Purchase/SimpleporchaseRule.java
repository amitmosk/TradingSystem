package Domain.StoreModule.Policy.Purchase;

import Domain.StoreModule.Basket;
import Domain.StoreModule.Policy.Predict;
import Domain.StoreModule.Product.Product;

import java.util.HashMap;
import java.util.Map;

public class SimpleporchaseRule implements porchaseRule {
    Predict predict;

    SimpleporchaseRule(Predict predict) {
        this.predict = predict;
    }

    @Override
    public boolean isApplying(Basket basket) {
        return predict.CanApply(18, basket);
    }
}
