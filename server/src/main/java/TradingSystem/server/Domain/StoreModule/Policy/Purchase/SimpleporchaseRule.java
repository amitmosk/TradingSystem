package TradingSystem.server.Domain.StoreModule.Policy.Purchase;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;

import java.util.HashMap;
import java.util.Map;

public class SimpleporchaseRule implements porchaseRule {
    Predict predict;

    public SimpleporchaseRule(Predict predict) {
        this.predict = predict;
    }

    @Override
    public boolean predictCheck(int age, Basket basket) {
        return predict.CanApply(age, basket);
    }
}
