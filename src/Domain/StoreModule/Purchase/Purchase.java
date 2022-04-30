package Domain.StoreModule.Purchase;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public abstract class Purchase {
    private int purchase_id;
    private double totalPrice;
    private LocalDate transaction_date;
    private Map<Integer, Integer> product_and_quantity;
    private Map<Integer, Double> product_and_totalPrice;

    public Purchase(int purchase_id, double totalPrice, Map<Integer, Integer> product_and_quantity, Map<Integer, Double> product_and_totalPrice) {
        this.purchase_id = purchase_id;
        this.totalPrice = totalPrice;
        transaction_date = LocalDate.now();
        this.product_and_quantity = product_and_quantity;
        this.product_and_totalPrice = product_and_totalPrice;
    }

    public Integer get_purchase_id()
    {
        return this.purchase_id;
    }
}