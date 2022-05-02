package Domain.StoreModule.Policy;

import Domain.StoreModule.Product.Product;

import java.util.List;

public interface Rule {
    boolean rule(List<Product> products);
}
