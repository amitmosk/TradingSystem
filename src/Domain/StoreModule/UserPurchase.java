package Domain.StoreModule;

import Domain.StoreModule.Purchase;

import java.util.List;
import java.util.Map;

public class UserPurchase extends Purchase {
    private Map<Integer, List<Integer>> storeId_productsIDS;

    public UserPurchase(int purchase_id, double totalPrice, Map<Integer, Integer> product_and_quantity, Map<Integer, Double> product_and_totalPrice) {
        super(purchase_id, totalPrice, product_and_quantity, product_and_totalPrice);
    }
}
