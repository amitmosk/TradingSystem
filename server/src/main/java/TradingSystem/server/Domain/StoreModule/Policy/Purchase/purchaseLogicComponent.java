package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "purchase_logic_type",
//        discriminatorType = DiscriminatorType.INTEGER)
public abstract class purchaseLogicComponent extends PurchaseRule {
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public PurchaseRule left;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public PurchaseRule right;

    public purchaseLogicComponent(PurchaseRule left, PurchaseRule right) {
        this.left = left;
        this.right = right;
    }

    public purchaseLogicComponent() {

    }
}

