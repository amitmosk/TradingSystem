package TradingSystem.server.Domain.StoreModule.Policy.Purchase;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;

import javax.persistence.*;

@Entity
@DiscriminatorValue("14")
public class SimplePurchaseRule extends PurchaseRule {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Predict predict;

    public SimplePurchaseRule(Predict predict) {
        this.predict = predict;
    }

    public SimplePurchaseRule() {

    }

    @Override
    public boolean predictCheck(int age, Basket basket) {
        return predict.CanApply(age, basket);
    }
}
