package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;

import java.util.List;

public abstract class abstractDiscountlogicComponent {
    public List<DiscountComponent> componentsList;
    public ComplexDiscountComponent discount;
    public abstractDiscountlogicComponent(List<DiscountComponent> componentsList, ComplexDiscountComponent discountComponent) {
        this.componentsList = componentsList;
        this.discount=discountComponent;
    }
}
