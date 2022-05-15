package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Basket;

public class AndporchaseRule implements porchaseRule {
    porchaseRule Left;
    porchaseRule Right;

    public AndporchaseRule(porchaseRule Left, porchaseRule Right) {
        this.Left = Left;
        this.Right = Right;
    }

    @Override
    public boolean predictCheck(int age, Basket basket) {
        return Left.predictCheck(age, basket) & Right.predictCheck(age, basket);
    }
}
