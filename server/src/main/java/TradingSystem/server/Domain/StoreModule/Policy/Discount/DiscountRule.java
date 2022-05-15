package Domain.StoreModule.Policy.Discount;

import Domain.StoreModule.Basket;

public interface DiscountRule {
    double CalculatePriceAfterDiscount(Basket basket);
    boolean CanApply(Basket basket);
}
