package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.PurchasePolicyException;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import java.util.HashMap;
import java.util.Set;

public class PurchasePolicy {
    private HashMap<String, porchaseRule> policy;

    public PurchasePolicy() {
        this.policy = new HashMap<String, porchaseRule>();
    }

    public void addRule(String name, porchaseRule rule) throws WrongPermterException {
        if (policy.get(name)!=null)
        policy.put(name, rule);
        else
            throw new WrongPermterException("there is a rule with this name already");
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
