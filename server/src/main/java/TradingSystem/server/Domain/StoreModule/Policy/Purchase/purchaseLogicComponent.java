package TradingSystem.server.Domain.StoreModule.Policy.Purchase;

import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Purchase.Purchase;

import java.util.List;

public class purchaseLogicComponent {
    public List<porchaseRule> componentsList;

    public purchaseLogicComponent(List<porchaseRule> lst) {
        this.componentsList = lst;
    }

    public void addItem(porchaseRule component) {
        componentsList.add(component);
    }

    public void removeItem(porchaseRule component) {
        componentsList.remove(component);
    }

}

