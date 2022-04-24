package Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Purchase {
    protected int purchase_id;
    protected double totalPrice;
    protected LocalDateTime transaction_date;
    protected Map<Integer, Integer> product_and_quantity;
    protected Map<Integer, Double> product_and_totalPrice;
    protected Map<Integer, String> product_and_name;

    protected double calculateTotalPrice(){
        return 0;
    }
}