package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.PurchasePolicyException;

import java.util.HashMap;
import java.util.Set;

public class PurchasePolicy {
    private HashMap<String, porchaseRule> policy;

    public PurchasePolicy() {
        this.policy = new HashMap<String, porchaseRule>();
    }

    public void addRule(porchaseRule rule) {
        policy.values().add(rule);
    }

    public void checkPolicy(int userAge, Basket basket) throws PurchasePolicyException {
        for (porchaseRule rule : policy.values()
        ) {
            if (rule.predictCheck(userAge, basket) == false)
                throw new PurchasePolicyException("check");
        }
    }

    public Set<String> getPolicyNames() {
        return policy.keySet();
    }

    public porchaseRule getPolicy(String name) {
        return policy.get(name);
    }

    public void removeRule(porchaseRule rule) {
        policy.remove(rule);
    }
}
