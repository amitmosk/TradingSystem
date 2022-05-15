package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;

/**
 * this class is a parent class.
 */

public class OrPurchaseRule implements PurchaseRule {
    private PurchaseRule Left;
    private PurchaseRule Right;

    public OrPurchaseRule(PurchaseRule Left, PurchaseRule Right) {
        this.Left = Left;
        this.Right = Right;
    }

    @Override
    public boolean ConfirmBuying(Basket basket) {
        return Left.ConfirmBuying(basket) || Right.ConfirmBuying(basket);
    }
}
