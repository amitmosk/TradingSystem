package TradingSystem.server.Domain.StoreModule.Policy.Discount;


import TradingSystem.server.Domain.StoreModule.Basket;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discount_component_type",
        discriminatorType = DiscriminatorType.INTEGER)
public abstract class DiscountComponent {
    @Id
    @GeneratedValue
    public Long id;

    public abstract double CalculateDiscount(Basket basket);

    abstract public boolean CanApply(Basket basket);

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
