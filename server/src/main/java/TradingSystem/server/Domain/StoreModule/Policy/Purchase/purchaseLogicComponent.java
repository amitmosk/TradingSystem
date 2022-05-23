package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Purchase.Purchase;

import java.util.List;

public class purchaseLogicComponent {
    public porchaseRule left;
    public porchaseRule right;

    public purchaseLogicComponent(porchaseRule left, porchaseRule right) {
        this.left = left;
        this.right = right;
    }

}

