package TradingSystem.server.Domain.StoreModule.Purchase;

import java.time.LocalDate;
import java.util.Map;

public class Purchase {

    private String transaction_date;
    private Map<Integer, Integer> product_and_quantity;
    private Map<Integer, Double> product_and_totalPrice;
    private Map<Integer, String> product_and_name;


    public Purchase(Map<Integer, Integer> product_and_quantity,
                    Map<Integer, Double> product_and_totalPrice, Map<Integer, String> product_and_name) {
        transaction_date = LocalDate.now().toString();
        this.product_and_quantity = product_and_quantity;
        this.product_and_totalPrice = product_and_totalPrice;
        this.product_and_name = product_and_name;
    }


    public String getTransaction_date() {
        return transaction_date;
    }

    public Map<Integer, Integer> getProduct_and_quantity() {
        return product_and_quantity;
    }

    public Map<Integer, Double> getProduct_and_totalPrice() {
        return product_and_totalPrice;
    }

    public Map<Integer, String> getProduct_and_name() {
        return product_and_name;
    }

    public boolean containsProduct(int productID) {
        return product_and_name.containsKey(productID);
    }

    public double getTotalPrice() {
        double answer = 0;
        for (Double price : product_and_totalPrice.values())
            answer = answer + price;
        return answer;
    }
}
