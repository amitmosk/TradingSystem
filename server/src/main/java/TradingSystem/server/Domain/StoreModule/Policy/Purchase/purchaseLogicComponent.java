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

    public PurchaseRule getLeft() {
        return left;
    }

    public void setLeft(PurchaseRule left) {
        this.left = left;
    }

    public PurchaseRule getRight() {
        return right;
    }

    public void setRight(PurchaseRule right) {
        this.right = right;
    }
}

