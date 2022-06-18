package TradingSystem.server.Domain.StoreModule.Purchase;

import TradingSystem.server.DAL.HibernateUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
public class StorePurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long store_purchase_history;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "store_purchases",
            joinColumns = {@JoinColumn(name = "store_purchase_history", referencedColumnName = "store_purchase_history")})
    @MapKeyColumn(name = "purchase_id") // the key column
    private Map<Integer, StorePurchase> purchaseID_purchases;
    private String store_name;

    // ------------------------------ constructors ------------------------------
    public StorePurchaseHistory(String store_name) {
        this.store_name = store_name;
        this.purchaseID_purchases = new ConcurrentHashMap<>();
    }

    public StorePurchaseHistory() {
    }

    // ------------------------------ getters ------------------------------


    public Map<Integer, StorePurchase> getPurchaseID_purchases() {
        return purchaseID_purchases;
    }

    public String getStore_name() {
        return store_name;
    }

    // ------------------------------ setters ------------------------------


    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void insert(StorePurchase purchase)
    {
        this.purchaseID_purchases.put(purchase.getPurchase_id(), purchase);
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append("Store " + store_name + " Purchase History:\n");
        for (StorePurchase p : this.purchaseID_purchases.values())
        {
            ans.append(p.toString());
        }
        return ans.toString();
    }

    public void setStore_purchase_history(Long store_purchase_history) {
        this.store_purchase_history = store_purchase_history;
    }

    public Long getStore_purchase_history() {
        return store_purchase_history;
    }

    public void setPurchaseID_purchases(Map<Integer, StorePurchase> purchaseID_purchases) {
        this.purchaseID_purchases = purchaseID_purchases;
    }
}
