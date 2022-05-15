package Domain.StoreModule.Policy.Purchase;

import Domain.StoreModule.Basket;

public class OrporchaseRule implements porchaseRule {
    porchaseRule Left;
    porchaseRule Right;

    public OrporchaseRule(porchaseRule Left, porchaseRule Right) {
        this.Left = Left;
        this.Right = Right;
    }

    @Override
    public boolean isApplying(Basket basket) {
        return Left.isApplying(basket) | Right.isApplying(basket);
    }
}
