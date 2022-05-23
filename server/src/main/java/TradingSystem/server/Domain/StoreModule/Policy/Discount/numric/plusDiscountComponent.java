package TradingSystem.server.Domain.StoreModule.Policy.Discount.numric;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;

public class plusDiscountComponent implements DiscountComponent {
    DiscountComponent simple1;
    DiscountComponent simple2;

    public plusDiscountComponent(DiscountComponent simple1, DiscountComponent simple2) {
        this.simple1 = simple1;
        this.simple2 = simple2;
    }

    public double CalculateDiscount(Basket basket) {
        double discount1 = simple1.CalculateDiscount(basket);
        double discount2 = simple2.CalculateDiscount(basket);
        return discount1 + discount2;
    }

    @Override
    public boolean CanApply(Basket basket) {
        return true;
    }
}