package TradingSystem.server.Domain.StoreModule.Policy.Discount;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Entity
public class DiscountPolicy {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "discount_policy",
            joinColumns = {@JoinColumn(name = "component_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "name") // the key column
    private Map<String, DiscountComponent> policy;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public DiscountPolicy() {
        this.policy = new HashMap<>();
    }

    private void checkUniqName(String name, Map map) throws WrongPermterException {
        if (map.keySet().contains(name))
            throw new WrongPermterException("There is a predict with this name in the store, please choose another name");
    }

    public void addRule(String name, DiscountComponent component) throws WrongPermterException {
        checkUniqName(name, policy);
        policy.put(name, component);
    }

    public void removeRule(String name) throws WrongPermterException {
        DiscountComponent toRemove = policy.remove(name);
        if (toRemove == null)
            throw new WrongPermterException("there is no discount with this name");
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

    public Map<String, DiscountComponent> getPolicy() {
        return policy;
    }

    public void setPolicy(Map<String, DiscountComponent> policy) {
        this.policy = policy;
    }
}
