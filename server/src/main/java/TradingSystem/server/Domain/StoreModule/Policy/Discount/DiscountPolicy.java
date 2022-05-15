package TradingSystem.server.Domain.StoreModule.Policy.Discount;

import TradingSystem.server.Domain.StoreModule.Basket;

import java.util.LinkedList;
import java.util.List;

public class DiscountPolicy {
    private List<DiscountRule> policy;

    public DiscountPolicy() {
        this.policy = new LinkedList();
    }

    public void addRule(DiscountRule rule) {
        policy.add(rule);
    }

    public void removeRule(int index) {
        policy.remove(index);
    }

    public double calculateDiscount(Basket basket) {
        double discount = 0;
        for (DiscountRule rule : policy)
            discount += rule.CalculateDiscount(basket);
        return discount;
    }
}
