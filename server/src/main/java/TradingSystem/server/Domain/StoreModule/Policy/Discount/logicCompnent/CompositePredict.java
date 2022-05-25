package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;

public abstract class CompositePredict implements Ipredict {

    Ipredict left;
    Ipredict right;

    public CompositePredict(Ipredict left, Ipredict right) {
        this.left = left;
        this.right = right;
    }
}
