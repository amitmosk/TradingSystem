package TradingSystem.server.Domain.StoreModule.Policy.Discount;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import java.util.HashMap;
import java.util.Set;

public class DiscountPolicy {
    private HashMap<String, DiscountComponent> policy;

    public DiscountPolicy() {
        this.policy = new HashMap<>();
    }

    public void addRule(DiscountComponent rule) {
        policy.values().add(rule);
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
}
