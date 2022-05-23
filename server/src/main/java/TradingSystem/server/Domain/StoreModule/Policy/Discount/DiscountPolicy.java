package TradingSystem.server.Domain.StoreModule.Policy.Discount;

import TradingSystem.server.Domain.StoreModule.Basket;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DiscountPolicy {
    private HashMap<String, DiscountComponent> policy;

    public DiscountPolicy() {
        this.policy = new HashMap<>();
    }

    public void addRule(DiscountComponent rule) {
        policy.values().add(rule);
    }

    public void removeRule(DiscountComponent component) {
        policy.remove(component);
    }

    public HashMap<String, DiscountComponent> getPolicy() {
        return policy;
    }

    public double calculateDiscount(Basket basket) {
        double discount = 0;
        for (DiscountComponent rule : policy.values())
            discount += rule.CalculateDiscount(basket);
        return discount;
    }
}
