package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.PurchasePolicyException;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import java.util.HashMap;
import java.util.Set;
//@OneToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "order_item_mapping",
//      joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")},
//      inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "id")})
//    @MapKey(name = "itemName")
public class PurchasePolicy {
    //name = the name of the table
    //joincolumn - name - the name of the foreinkey of the map
    // referenced column name = the father PK

    // the value column
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
}
