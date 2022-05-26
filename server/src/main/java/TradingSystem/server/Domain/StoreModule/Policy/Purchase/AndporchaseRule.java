package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;

import java.util.List;

public class AndporchaseRule extends purchaseLogicComponent implements porchaseRule {


    public AndporchaseRule(porchaseRule left, porchaseRule right) {
        super(left, right);
    }

    @Override
    public boolean predictCheck(int age, Basket basket) {
        return left.predictCheck(age, basket) && right.predictCheck(age, basket);
    }
}
