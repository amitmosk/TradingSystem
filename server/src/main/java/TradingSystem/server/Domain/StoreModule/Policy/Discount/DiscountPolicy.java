package TradingSystem.server.Domain.StoreModule.Policy.Discount;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Set;
@Entity
public class DiscountPolicy {
    @Transient //sinlgetone
    private HashMap<String, DiscountComponent> policy;
    @Id
    @GeneratedValue
    private Long id;

    public DiscountPolicy() {
        this.policy = new HashMap<>();
    }

    public void addRule(String name, DiscountComponent rule) throws WrongPermterException {
        if (policy.get(name) != null)
            policy.put(name, rule);
        else
            throw new WrongPermterException("there is a rule with this name allready");
    }

    public void removeRule(String name) {
        policy.remove(policy.get(name));
    }


    public DiscountComponent getDiscountCompnentByName(String name) throws WrongPermterException {
        DiscountComponent Toreturn = policy.get(name);
        if (Toreturn == null)
            throw new WrongPermterException("there is no discount compnent with this name");
        return Toreturn;
    }

    public Set<String> getPolicyNames() {
        return policy.keySet();
    }

    public double calculateDiscount(Basket basket) {
        double discount = 0;
        for (DiscountComponent rule : policy.values())
            discount += rule.CalculateDiscount(basket);
        return discount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
