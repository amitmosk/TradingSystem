package TradingSystem.server.Domain.StoreModule.Policy.Purchase;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;

public class SimplePurchaseRule extends PurchaseRule {
    Predict predict;

    public SimplePurchaseRule(Predict predict) {
        this.predict = predict;
    }

    @Override
    public boolean predictCheck(int age, Basket basket) {
        return predict.CanApply(age, basket);
    }
}
