package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.PurchasePolicyException;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Set;

@Entity
public class PurchasePolicy {
    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private HashMap<String, PurchaseRule> policy;
    private Long policyId;

    public PurchasePolicy() {
        this.policy = new HashMap<String, PurchaseRule>();
    }

    public void addRule(String name, PurchaseRule rule) throws WrongPermterException {
        if (policy.get(name) != null)
            policy.put(name, rule);
        else
            throw new WrongPermterException("there is a rule with this name allready");
    }

    public void checkPolicy(int userAge, Basket basket) throws PurchasePolicyException {
        for (PurchaseRule rule : policy.values()
        ) {
            if (!rule.predictCheck(userAge, basket))
                throw new PurchasePolicyException("the purchase doesnt stands with the policy of the store");
        }
    }

    public Set<String> getPolicyNames() {
        return policy.keySet();
    }

    public PurchaseRule getPolicy(String name) throws WrongPermterException {
        PurchaseRule rule = policy.get(name);
        if (rule == null)
            throw new WrongPermterException("no policy with this name");
        return rule;
    }

    public void removeRule(PurchaseRule rule) {
        policy.remove(rule);
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
