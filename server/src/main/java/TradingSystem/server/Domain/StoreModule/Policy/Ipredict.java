package TradingSystem.server.Domain.StoreModule.Policy;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;

import javax.persistence.*;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "predcit_type",
        discriminatorType = DiscriminatorType.INTEGER)
public abstract class Ipredict {

    @Id
    @GeneratedValue
    protected Long id;

    public abstract boolean CanApply(Basket b);

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
