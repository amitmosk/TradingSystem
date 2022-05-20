package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;

import java.util.List;

public class AndporchaseRule extends purchaseLogicComponent implements porchaseRule {


    public AndporchaseRule(List<porchaseRule> lst) {
        super(lst);
    }

    @Override
    public boolean predictCheck(int age, Basket basket) {
        for (porchaseRule compnent : componentsList) {
            if (!compnent.predictCheck(age, basket))
                return false;
        }
        return true;
    }
}

