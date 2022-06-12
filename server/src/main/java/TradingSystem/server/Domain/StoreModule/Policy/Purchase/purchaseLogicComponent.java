package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "purchase_logic_type",
        discriminatorType = DiscriminatorType.INTEGER)
public abstract class purchaseLogicComponent extends PurchaseRule {
    @OneToOne
    public PurchaseRule left;
    @OneToOne
    public PurchaseRule right;

    public purchaseLogicComponent(PurchaseRule left, PurchaseRule right) {
        this.left = left;
        this.right = right;
    }

    public purchaseLogicComponent() {

    }
}

