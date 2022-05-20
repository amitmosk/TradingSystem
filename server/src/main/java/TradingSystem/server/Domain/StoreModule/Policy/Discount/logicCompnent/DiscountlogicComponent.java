package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;

import java.util.List;

public abstract class DiscountlogicComponent {
    public List<DiscountComponent> componentsList;
    public ComplexDiscountComponent discount;
    public DiscountlogicComponent(List<DiscountComponent> componentsList, ComplexDiscountComponent discountComponent) {
        this.componentsList = componentsList;
        this.discount=discountComponent;
    }

    public void addItem(DiscountComponent component){
        componentsList.add(component);
    }

    public void removeItem(DiscountComponent component){
        componentsList.remove(component);
    }


}
