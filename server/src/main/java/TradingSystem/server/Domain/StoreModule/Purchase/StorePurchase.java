package TradingSystem.server.Domain.StoreModule.Purchase;

import TradingSystem.server.DAL.HibernateUtils;

import javax.persistence.*;
import java.util.Map;

@Entity
public class StorePurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long store_purchase_id;
    private int purchase_id;
    private String buyer_email;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Purchase purchase;

    // ------------------------------ constructors ------------------------------
    public StorePurchase(Purchase purchase, String buyer_email, int purchase_id) {
        this.buyer_email = buyer_email;
        this.purchase = purchase;
        this.purchase_id = purchase_id;
        HibernateUtils.persist(this);
    }

    public StorePurchase() {
    }

    // ------------------------------ getters ------------------------------

    public Integer getPurchase_id()
    {
        return this.purchase_id;
    }


    public double getTotalPrice() {
        return purchase.getTotalPrice();
    }

    public String getTransaction_date() {
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

    // ------------------------------ setters ------------------------------


    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public void setPurchase_id(int purchase_id) {
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

    public void setStore_purchase_id(Long store_purchase_id) {
        this.store_purchase_id = store_purchase_id;
    }

    public Long getStore_purchase_id() {
        return store_purchase_id;
    }
}





