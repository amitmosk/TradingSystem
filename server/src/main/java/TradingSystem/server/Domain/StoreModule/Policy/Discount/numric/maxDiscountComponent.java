package TradingSystem.server.Domain.StoreModule.Policy.Discount.numric;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("5")
public class maxDiscountComponent extends DiscountComponent {
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    DiscountComponent left;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    DiscountComponent right;

    public maxDiscountComponent(DiscountComponent left, DiscountComponent right) {
        this.left = left;
        this.right = right;
    }

    public maxDiscountComponent() {

    }

    public double CalculateDiscount(Basket basket) {
        double discount1 = left.CalculateDiscount(basket);
        double discount2 = right.CalculateDiscount(basket);
        return Double.max(discount1, discount2);
    }

    @Override
    public boolean CanApply(Basket basket) {
        return true;
    }
}