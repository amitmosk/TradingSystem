package Domain.StoreModule.Purchase;

import java.util.Map;

public class StorePurchase extends Purchase {
    private String buyer_email;


    public StorePurchase(String buyer_email, int purchase_id, double totalPrice,
                         Map<Integer, Integer> product_and_quantity, Map<Integer, Double> product_and_totalPrice) {
        super(purchase_id, totalPrice, product_and_quantity, product_and_totalPrice);
        this.buyer_email = buyer_email;
    }


}





