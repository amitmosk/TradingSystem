package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.Utils.Exception.PurchasePolicyException;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class PurchasePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "purchase_policy",
            joinColumns = {@JoinColumn(name = "rule_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "name") // the key column
    private Map<String, PurchaseRule> policy;
    private Long policyId;

    public PurchasePolicy() {
        this.policy = new HashMap<String, PurchaseRule>();
    }

    private void checkUniqName(String name, Map map) throws WrongPermterException {
        if (map.keySet().contains(name))
            throw new WrongPermterException("there is a predict with this name in the store,please choose another name");
    }

    public void addRule(String name, PurchaseRule rule) throws WrongPermterException {
        checkUniqName(name, policy);
        policy.put(name, rule);
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

    public PurchaseRule getRule(String name) throws WrongPermterException {
        PurchaseRule rule = policy.get(name);
        if (rule == null)
            throw new WrongPermterException("no policy with this name");
        return rule;
    }

    public void removeRule(String name) throws WrongPermterException {
        PurchaseRule toRemove = policy.remove(name);
        if (toRemove == null)
            throw new WrongPermterException("there is no rule with this name");
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

    public Map<String, PurchaseRule> getRule() {
        return policy;
    }

    public void setPolicy(Map<String, PurchaseRule> policy) {
        this.policy = policy;
    }
}
