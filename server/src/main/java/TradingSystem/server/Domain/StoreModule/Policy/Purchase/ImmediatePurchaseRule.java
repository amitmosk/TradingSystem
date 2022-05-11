package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;


/**
 *  this class is a leaf class.
 */

public class ImmediatePurchaseRule implements PurchaseRule {
    private Predict predict;

    public ImmediatePurchaseRule(Predict predict) {
        this.predict = predict;
    }

    @Override
    public boolean ConfirmBuying(Basket basket) {
        return true;
        // TODO : return predict.CanBuy(basket);
    }
}
