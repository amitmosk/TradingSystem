package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "purchase_rule_type",
        discriminatorType = DiscriminatorType.INTEGER)
public abstract class PurchaseRule {
    @Id
    @GeneratedValue
    protected Long id;

    public abstract boolean predictCheck(int age, Basket basket);


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
