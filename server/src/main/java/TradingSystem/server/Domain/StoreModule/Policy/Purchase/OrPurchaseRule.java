package TradingSystem.server.Domain.StoreModule.Policy.Purchase;


import TradingSystem.server.Domain.StoreModule.Basket;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OrPurchaseRule")
public class OrPurchaseRule extends purchaseLogicComponent {

    public OrPurchaseRule(PurchaseRule left, PurchaseRule right) {
        super(left, right);
    }

    public OrPurchaseRule() {

    }

    @Override
    public boolean predictCheck(int age, Basket basket) {
        return left.predictCheck(age, basket) || right.predictCheck(age, basket);
    }
}


