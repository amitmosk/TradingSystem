package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.PurchasePolicyException;

import java.util.LinkedList;
import java.util.List;

public class PurchasePolicy {
    private List<porchaseRule> policy;

    public PurchasePolicy() {
        this.policy = new LinkedList();
    }

    public void addRule(porchaseRule rule) {
        policy.add(rule);
    }

    public void checkPolicy(int userAge, Basket basket) throws PurchasePolicyException {
        for (porchaseRule rule : policy
        ) {
            if (rule.predictCheck(userAge, basket)==false)
                throw new PurchasePolicyException("check");
        }
    }

    public void removeRule(porchaseRule rule) {
        policy.remove(rule);
    }
}
