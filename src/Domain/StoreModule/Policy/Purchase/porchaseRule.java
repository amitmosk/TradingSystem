package Domain.StoreModule.Policy.Purchase;

import Domain.StoreModule.Basket;

public interface porchaseRule {

    public boolean isApplying(Basket basket);

}
