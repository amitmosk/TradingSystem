package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

public abstract class purchaseLogicComponent extends PurchaseRule {
    public PurchaseRule left;
    public PurchaseRule right;

    public purchaseLogicComponent(PurchaseRule left, PurchaseRule right) {
        this.left = left;
        this.right = right;
    }

}

