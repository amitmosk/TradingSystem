package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;

public class AndPurchaseRule extends purchaseLogicComponent {


    public AndPurchaseRule(PurchaseRule left, PurchaseRule right) {
        super(left, right);
    }

    @Override
    public boolean predictCheck(int age, Basket basket) {
        return left.predictCheck(age, basket) && right.predictCheck(age, basket);
    }
}

