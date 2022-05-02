package Domain.StoreModule.Purchase;

import java.time.LocalDate;
import java.util.Map;

public class StorePurchase {
    private String buyer_email;
    private Purchase purchase;
    private int purchase_id;

    public StorePurchase(Purchase purchase, String buyer_email, int purchase_id) {
        this.buyer_email = buyer_email;
        this.purchase = purchase;
        this.purchase_id = purchase_id;
    }

    @Override
    public String toString() {
        return "StorePurchase{" +
                "buyer_email='" + buyer_email + '\'' +
                ", totalPrice=" + this.getTotalPrice() +
                ", transaction_date=" + this.getTransaction_date() +
                ", product_and_quantity=" + this.getProduct_and_quantity() +
                ", product_and_totalPrice=" + this.getProduct_and_totalPrice() +
                ", product_and_name=" + this.getProduct_and_name() +

                '}';
    }

    public Integer getPurchase_id()
    {
        return this.purchase_id;
    }


    public double getTotalPrice() {
        return purchase.getTotalPrice();
    }

    public LocalDate getTransaction_date() {
        return this.purchase.getTransaction_date();
    }

    public Map<Integer, Integer> getProduct_and_quantity() {
        return this.purchase.getProduct_and_quantity();
    }

    public Map<Integer, Double> getProduct_and_totalPrice() {
        return this.purchase.getProduct_and_totalPrice();
    }

    public Map<Integer, String> getProduct_and_name() {
        return this.purchase.getProduct_and_name();
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public String getBuyer_email() {
        return buyer_email;
    }
}





