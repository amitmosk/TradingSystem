package Domain.StoreModule.Purchase;

import java.util.Map;

public class StorePurchase extends Purchase {
    private int buyer_id;


    public StorePurchase(int buyer_id, int purchase_id, double totalPrice,
                         Map<Integer, Integer> product_and_quantity, Map<Integer, Double> product_and_totalPrice) {
        super(purchase_id, totalPrice, product_and_quantity, product_and_totalPrice);
        this.buyer_id = buyer_id;
    }


}





