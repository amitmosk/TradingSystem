package TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent;

import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "composite_predict_type",
        discriminatorType = DiscriminatorType.INTEGER)
public abstract class CompositePredict extends Ipredict {
    @ManyToOne
    Ipredict left;
    @ManyToOne
    Ipredict right;

    public CompositePredict(Ipredict left, Ipredict right) {
        this.left = left;
        this.right = right;
    }

    public CompositePredict() {

    }

}
