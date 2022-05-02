package Domain.UserModule;

import java.time.LocalDateTime;
import java.util.Map;

abstract class Purchase {
    protected int purchase_id;
    protected double totalPrice;
    protected LocalDateTime transaction_date;
    protected Map<Integer, Integer> product_and_quantity;
    protected Map<Integer, Double> product_and_totalPrice;
    protected Map<Integer, String> product_and_name;

    protected double calculateTotalPrice(){
        double t_price = 0;
        for(double price : product_and_totalPrice.values()){
            t_price+=price;
        }
        return t_price;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}