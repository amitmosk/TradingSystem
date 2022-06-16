package TradingSystem.server.Domain.StoreModule.Policy.Discount;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;

import javax.persistence.*;

@Entity
@DiscriminatorValue("1")
public class ComplexDiscountComponent extends DiscountComponent {
    @OneToOne
    DiscountComponent rule;
    @ManyToOne
    Ipredict predict;

    public DiscountComponent getRule() {
        return rule;
    }

    public void setRule(DiscountComponent rule) {
        this.rule = rule;
    }

    public Ipredict getPredict() {
        return predict;
    }

    public void setPredict(Ipredict predict) {
        this.predict = predict;
    }

    public ComplexDiscountComponent(DiscountComponent rule, Ipredict predict) {
        this.predict = predict;
        this.rule = rule;
    }

    public ComplexDiscountComponent() {

    }

    public boolean CanApply(Basket basket) {
        return predict.CanApply(basket);
    }

    public double CalculateDiscount(Basket basket) {
        if (CanApply(basket))
            return rule.CalculateDiscount(basket);
        else
            return 0;
    }
}