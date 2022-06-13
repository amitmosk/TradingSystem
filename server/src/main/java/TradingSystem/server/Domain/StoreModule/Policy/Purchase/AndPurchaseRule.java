package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("12")
public class AndPurchaseRule extends purchaseLogicComponent {

    public AndPurchaseRule(PurchaseRule left, PurchaseRule right) {
        super(left, right);
    }

    public AndPurchaseRule() {

    }

    @Override
    public boolean predictCheck(int age, Basket basket) {
        return left.predictCheck(age, basket) && right.predictCheck(age, basket);
    }
}

