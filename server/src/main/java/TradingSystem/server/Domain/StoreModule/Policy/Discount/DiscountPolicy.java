package TradingSystem.server.Domain.StoreModule.Policy.Discount;

import TradingSystem.server.Domain.StoreModule.Basket;

import java.util.LinkedList;
import java.util.List;

public class DiscountPolicy {
    private List<DiscountComponent> policy;

    public DiscountPolicy() {
        this.policy = new LinkedList();
    }

    public void addRule(DiscountComponent rule) {
        policy.add(rule);
    }

    public void removeRule(int index) {
        policy.remove(index);
    }

    public double calculateDiscount(Basket basket) {
        double discount = 0;
        for (DiscountComponent rule : policy)
            discount += rule.CalculateDiscount(basket);
        return discount;
    }
}
